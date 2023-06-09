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
	@Transactional(rollbackFor = {SoldOutException.class,NotExistProductException.class,Exception.class})
	public void insertOrderApp(OrderAppDTO dto,List<String> productNumList) throws Exception {
		if(lock.tryLock(10, TimeUnit.SECONDS)) {
			
			try {
				//pk ����
				String orderNum = orderAppRepository.selectOrderNumMax();
				dto.setOrderNum(orderNum);
				
				OrderApp orderApp = new OrderApp(dto);
				
				//���� ��ǰ ��ȸ
				List<Product> productList = productRepository.selectProductNumListIn(productNumList);
				
				//��ǰ ��ȿ�� üũ
				for(OrderAppItemDTO itemDTO : dto.getItemList()) {
					
					//�ش� ��ǰ ���� ���� filter üũ
					Product product = productList.stream().filter(x->x.getProductNum().equals(itemDTO.getProductNum())).findFirst().orElse(null);
					if(product == null) {
						throw new NotExistProductException("�ش� ��ǰ�� �������� �ʽ��ϴ�.");
					}
					
					//��� üũ
					productService.validateProductCntCheck(product.getProductNum(),(product.getCnt() - itemDTO.getCnt()));
					
					//��ǰ ��� ����Ʈ
					OrderAppItem orderAppItem = new OrderAppItem(itemDTO,orderApp,product);
					orderApp.addItemList(orderAppItem);
					
					//��� ���̳ʽ� ó��
					product.minusProductCnt(itemDTO.getCnt());
				}
				
				//���� ȭ�� ���
				PayAppDisplayUtil.productPayDisPlay(orderApp,dto.getItemList());	
				
				//�ֹ� ���� ���
				orderAppRepository.save(orderApp);
			}catch (Exception e) {
				throw new Exception(e.getMessage());
			}finally {
				lock.unlock();
			}
			
		}
	}
}
