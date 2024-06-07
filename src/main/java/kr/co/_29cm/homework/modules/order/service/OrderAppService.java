package kr.co._29cm.homework.modules.order.service;

import kr.co._29cm.homework.exception.NotExistException;
import kr.co._29cm.homework.exception.OverlapException;
import kr.co._29cm.homework.exception.SoldOutException;
import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppDefaultDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppItemDTO;
import kr.co._29cm.homework.modules.order.entity.OrderApp;
import kr.co._29cm.homework.modules.order.entity.OrderAppItem;
import kr.co._29cm.homework.modules.order.repository.OrderAppRepository;
import kr.co._29cm.homework.modules.product.entity.Product;
import kr.co._29cm.homework.modules.product.repository.ProductRepository;
import kr.co._29cm.homework.modules.product.service.ProductService;
import kr.co._29cm.homework.modules.util.PayAppDisplayUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.order.service
* @fileName      : OrderAppService.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 주문 처리 service
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       		 최초생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderAppService{

	private final OrderAppRepository orderAppRepository;

	private final ProductService productService;

	private final ProductRepository productRepository;
	
	private static final Lock lock = new ReentrantLock();
	

	
	
	/**
	 * 주문 정보 조회
	 * @param searchDTO
	 * @return
	 * @throws Exception
	 */
	public Page<OrderAppDTO> selectOrderAppList(OrderAppDefaultDTO searchDTO) throws Exception {
		return orderAppRepository.selectOrderAppPageList(searchDTO);
	}
	
	/**
	 * 주문 정보 결제 처리
	 * @param dto
	 * @throws Exception
	 */
	@Transactional(rollbackFor = {SoldOutException.class,NotExistException.class,OverlapException.class,Exception.class})
	public void insertOrderApp(OrderAppDTO dto,List<String> productNumList) throws Exception {
		if(lock.tryLock(10, TimeUnit.SECONDS)) {
			
			try {
				//주문번호 pk 생성
				String orderNum = orderAppRepository.selectOrderNumMax();
				dto.setOrderNum(orderNum);
				
				OrderApp orderApp = new OrderApp(dto);
				
				//주문 상품 번호 조회
				List<Product> productList = productRepository.selectProductNumListIn(productNumList);
				
				//주문 상품 정보에 대한 유효성 체크
				for(OrderAppItemDTO itemDTO : dto.getItemList()) {
					
					//상품 유효성 체크					
					Product product = productList.stream().filter(x->x.getProductNum().equals(itemDTO.getProductNum())).findFirst().orElse(null);
					List<String> list = productNumList.stream().filter(x->x.equals(itemDTO.getProductNum())).collect(Collectors.toList());
					
					if(product == null) {
						throw new NotExistException("NotExistException 발생. 해당 '"+itemDTO.getProductNum()+"' 상품은 존재하지 않는 상품입니다.");
					}else if(list.size() > 1) {
						throw new OverlapException("OverlapException 발생. 해당 '"+itemDTO.getProductNum()+"' 상품은 중복신청된 상품입니다.");
					}
					
					//상품 재고량 체크
					productService.validateProductCntCheck(product.getProductNum(),(product.getCnt() - itemDTO.getCnt()));
					
					//주문 상품 정보 담기
					OrderAppItem orderAppItem = new OrderAppItem(itemDTO,orderApp,product);
					orderApp.addItemList(orderAppItem);
					
					//상품 정보 재고량 마이너스 처리
					product.minusProductCnt(itemDTO.getCnt());
				}
				
				//결제 화면 출력
				PayAppDisplayUtil.productPayDisPlay(orderApp,dto.getItemList());	
				
				//주문 정보 등록
				orderAppRepository.save(orderApp);
			}catch (Exception e) {
				throw new Exception(e.getMessage());
			}finally {
				lock.unlock();
			}
			
		}
	}
}
