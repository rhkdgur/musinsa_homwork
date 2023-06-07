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
import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppDefaultDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppItemDTO;
import kr.co._29cm.homework.modules.order.entity.OrderApp;
import kr.co._29cm.homework.modules.order.entity.QOrderApp;
import kr.co._29cm.homework.modules.order.repository.OrderAppItemRepository;
import kr.co._29cm.homework.modules.order.repository.OrderAppRepository;
import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import kr.co._29cm.homework.modules.product.repository.ProductRepository;
import kr.co._29cm.homework.modules.product.service.ProductService;

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
	private OrderAppItemRepository orderAppItemRepository;
	
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
	@Transactional
	public void insertOrderApp(OrderAppDTO dto) throws Exception {
		if(lock.tryLock(10, TimeUnit.SECONDS)) {
			
			try {
				//pk 생성
				String orderNum = orderAppRepository.selectOrderNumMax();
				dto.setOrderNum(orderNum);
				
				//주문 정보 등록
				orderAppRepository.save(dto.entity());
				//주문 상품 정보 등록
				for(OrderAppItemDTO itemDTO : dto.getItemList()) {
					itemDTO.setOrderNum(orderNum);
					orderAppItemRepository.save(itemDTO.entity());
				}
				
				// 상품 재고 업데이트
				for(ProductDTO productDTO : dto.getCheckProductList()) {
					productRepository.updateProductCnt(productDTO.entity());
				}
			}catch (Exception e) {
				System.out.println(e.getMessage());
				throw new Exception(e.getMessage());
			}finally {
				lock.unlock();
			}
			
		}
	}
}
