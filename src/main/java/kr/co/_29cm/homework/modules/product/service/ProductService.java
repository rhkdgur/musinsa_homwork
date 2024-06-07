package kr.co._29cm.homework.modules.product.service;

import kr.co._29cm.homework.exception.SoldOutException;
import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import kr.co._29cm.homework.modules.product.dto.ProductDefaultDTO;
import kr.co._29cm.homework.modules.product.entity.Product;
import kr.co._29cm.homework.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.product.service
* @fileName      : ProductService.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 상품 service
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       		최초생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

	private final ProductRepository productRepository;
	
	/**
	 * 상품 목록 조회
	 * @return
	 * @throws Exception
	 */
	public Page<ProductDTO> selectProductList(ProductDefaultDTO searchDTO) throws Exception{
		return productRepository.selectProductPageList(searchDTO);
	}
	
	/**
	 * 상품 상세 조회
	 * @param productDTO
	 * @return
	 * @throws Exception
	 */
	public ProductDTO selectProduct(ProductDTO productDTO) throws Exception {
		Product product = productRepository.findById(productDTO.getProductNum()).orElse(null);
		return product == null ? null : new ProductDTO(product);
	}
	
	/**
	 * 상품 재고 업데이트
	 * @param dto
	 * @throws Exception
	 */
	@Transactional
	public void updateProductCnt(ProductDTO dto) throws Exception {
		productRepository.updateProductCnt(dto.entity());
	}
	
	/**
	 * 상품 재고량 유효성 체크
	 * @param productNum
	 * @param cnt
	 * @throws SoldOutException
	 */
	public void validateProductCntCheck(String productNum,int cnt) throws SoldOutException{
		if(cnt < 0) {
			throw new SoldOutException("SoldOutException 발생. 주문한 "+productNum+"은 상품량이 재고량보다 큽니다.");
		}
	}

}
