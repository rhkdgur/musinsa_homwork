package kr.co._29cm.homework.modules.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import kr.co._29cm.homework.common.service.BaseService;
import kr.co._29cm.homework.exception.SoldOutException;
import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import kr.co._29cm.homework.modules.product.dto.ProductDefaultDTO;
import kr.co._29cm.homework.modules.product.entity.Product;
import kr.co._29cm.homework.modules.product.entity.QProduct;
import kr.co._29cm.homework.modules.product.repository.ProductRepository;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.product.service
* @fileName      : ProductService.java
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
public class ProductService extends BaseService{

	@Autowired
	private ProductRepository productRepository;
	
	// ���� ����
	BooleanBuilder commonQuery(ProductDefaultDTO searchDTO) {
		BooleanBuilder builder = new BooleanBuilder();
		QProduct qProduct = QProduct.product;
		
		if(searchDTO.getProductNum() != null && !searchDTO.getProductNum().isEmpty()) {
			builder.and(qProduct.productNum.eq(searchDTO.getProductNum()));
		}
		
		return builder;
	}
	
	/**
	 * ��ǰ ��� ��ȸ
	 * @return
	 * @throws Exception
	 */
	public Page<ProductDTO> selectProductList(ProductDefaultDTO searchDTO) throws Exception{
		
		QProduct qProduct = QProduct.product;
		
		Long cnt = jpaQuery.select(qProduct.count()).from(qProduct)
				.where(commonQuery(searchDTO)).fetchFirst();
		
		List<Product> list = jpaQuery.selectFrom(qProduct)
								.where(commonQuery(searchDTO))
								.offset(searchDTO.getPageable().getOffset())
								.limit(searchDTO.getPageable().getPageSize()).fetch();		
		
		return new PageImpl<>(list.stream().map(x-> new ProductDTO(x)).collect(Collectors.toList()),searchDTO.getPageable(),cnt);
	}
	
	/**
	 * ��ǰ �� ��ȸ
	 * @param productDTO
	 * @return
	 * @throws Exception
	 */
	public ProductDTO selectProduct(ProductDTO productDTO) throws Exception {
		Product product = productRepository.findById(productDTO.getProductNum()).orElse(null);
		return product == null ? null : new ProductDTO(product);
	}
	
	/**
	 * ��ǰ ��� ������Ʈ
	 * @param dto
	 * @throws Exception
	 */
	@Transactional
	public void updateProductCnt(ProductDTO dto) throws Exception {
		productRepository.updateProductCnt(dto.entity());
	}
	
	/**
	 * ��ǰ ��� ��ȿ�� üũ
	 * @param dto
	 * @throws SoldOutException
	 */
	public void validateProductCntCheck(String productNum,int cnt) throws SoldOutException{
		if(cnt < 0) {
			throw new SoldOutException("SoldOutException �߻�. �ֹ���ȣ "+productNum+"�� ��ǰ���� ������� Ů�ϴ�.");
		}
	}

}
