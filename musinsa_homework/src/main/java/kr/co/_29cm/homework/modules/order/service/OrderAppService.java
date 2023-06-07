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
* @description   : ��ǰ service
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       ���� ����
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
	 * �ֹ� ���� ����
	 * @param searchDTO
	 * @return
	 */
	BooleanBuilder commonQuery(OrderAppDefaultDTO searchDTO) {
		BooleanBuilder builder = new BooleanBuilder();
		QOrderApp qOrderApp = QOrderApp.orderApp;
		
		//�ֹ���ȣ�� ���� ���
		if(searchDTO.getOrderNum() != null && !searchDTO.getOrderNum().isEmpty()) {
			builder.and(qOrderApp.orderNum.eq(searchDTO.getOrderNum()));
		}
		
		return builder;
	}
	
	
	/**
	 * �ֹ� ��� ��ȸ
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
	 * �ֹ� ���
	 * @param dto
	 * @throws Exception
	 */
	@Transactional
	public void insertOrderApp(OrderAppDTO dto) throws Exception {
		if(lock.tryLock(10, TimeUnit.SECONDS)) {
			
			try {
				//pk ����
				String orderNum = orderAppRepository.selectOrderNumMax();
				dto.setOrderNum(orderNum);
				
				//�ֹ� ���� ���
				orderAppRepository.save(dto.entity());
				//�ֹ� ��ǰ ���� ���
				for(OrderAppItemDTO itemDTO : dto.getItemList()) {
					itemDTO.setOrderNum(orderNum);
					orderAppItemRepository.save(itemDTO.entity());
				}
				
				// ��ǰ ��� ������Ʈ
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
