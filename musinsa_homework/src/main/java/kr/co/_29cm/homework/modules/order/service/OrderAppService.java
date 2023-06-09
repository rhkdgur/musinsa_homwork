package kr.co._29cm.homework.modules.order.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import kr.co._29cm.homework.common.service.BaseService;
import kr.co._29cm.homework.exception.NotExistProductException;
import kr.co._29cm.homework.exception.SoldOutException;
import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppDefaultDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppItemDTO;
import kr.co._29cm.homework.modules.order.entity.OrderApp;
import kr.co._29cm.homework.modules.order.entity.OrderAppItem;
import kr.co._29cm.homework.modules.order.entity.QOrderApp;
import kr.co._29cm.homework.modules.order.repository.OrderAppItemRepository;
import kr.co._29cm.homework.modules.order.repository.OrderAppRepository;
import kr.co._29cm.homework.modules.product.entity.Product;
import kr.co._29cm.homework.modules.product.repository.ProductRepository;
import kr.co._29cm.homework.modules.product.service.ProductService;
import kr.co._29cm.homework.modules.util.PayAppDisplayUtil;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.order.service
* @fileName      : OrderAppService.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 상품 service
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       최초 생성
 */
@Service
@Transactional(readOnly = true)
public class OrderAppService extends BaseService{

	@Autowired
	private OrderAppRepository orderAppRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	private static final Lock lock = new ReentrantLock();
	
	/**
	 * 주문 공통 쿼리
	 * @param searchDTO
	 * @return
	 */
	BooleanBuilder commonQuery(OrderAppDefaultDTO searchDTO) {
		BooleanBuilder builder = new BooleanBuilder();
		QOrderApp qOrderApp = QOrderApp.orderApp;
		
		//주문번호가 있을 경우
		if(searchDTO.getOrderNum() != null && !searchDTO.getOrderNum().isEmpty()) {
			builder.and(qOrderApp.orderNum.eq(searchDTO.getOrderNum()));
		}
		
		return builder;
	}
	
	
	/**
	 * 주문 목록 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Page<OrderAppDTO> selectOrderAppList(OrderAppDefaultDTO searchDTO) throws Exception {
		
		QOrderApp qOrderApp = QOrderApp.orderApp;
		Long cnt = jpaQuery.select(qOrderApp.count())
						.from(qOrderApp).where(commonQuery(searchDTO)).fetchFirst();
		
		List<OrderApp> list  = jpaQuery.selectFrom(qOrderApp)
											.where(commonQuery(searchDTO))
											.offset(searchDTO.getPageable().getOffset())
											.limit(searchDTO.getPageable().getPageSize())
											.fetch();
		
		return new PageImpl<>(list.stream().map(x-> new OrderAppDTO(x)).collect(Collectors.toList()),searchDTO.getPageable(),cnt);
	}
	
	/**
	 * 주문 등록
	 * @param dto
	 * @throws Exception
	 */
	@Transactional(rollbackFor = {SoldOutException.class,NotExistProductException.class,Exception.class})
	public void insertOrderApp(OrderAppDTO dto,List<String> productNumList) throws Exception {
		if(lock.tryLock(10, TimeUnit.SECONDS)) {
			
			try {
				//pk 생성
				String orderNum = orderAppRepository.selectOrderNumMax();
				dto.setOrderNum(orderNum);
				
				OrderApp orderApp = new OrderApp(dto);
				
				//구매 상품 조회
				List<Product> productList = productRepository.selectProductNumListIn(productNumList);
				
				//상품 유효성 체크
				for(OrderAppItemDTO itemDTO : dto.getItemList()) {
					
					//해당 상품 존재 여부 filter 체크
					Product product = productList.stream().filter(x->x.getProductNum().equals(itemDTO.getProductNum())).findFirst().orElse(null);
					if(product == null) {
						throw new NotExistProductException("해당 제품은 존재하지 않습니다.");
					}
					
					//재고량 체크
					productService.validateProductCntCheck(product.getProductNum(),(product.getCnt() - itemDTO.getCnt()));
					
					//상품 목록 리스트
					OrderAppItem orderAppItem = new OrderAppItem(itemDTO,orderApp,product);
					orderApp.addItemList(orderAppItem);
					
					//재고량 마이너스 처리
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
