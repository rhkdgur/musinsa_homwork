package kr.co._29cm.homework.modules.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import kr.co._29cm.homework.modules.product.entity.Product;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.product.repository
* @fileName      : ProductRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 상품 레포지토리
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       최초 생성
 */
public interface ProductRepository extends JpaRepository<Product, String>{
	
	@Query("SELECT p from Product p where p.productNum IN (:productNum)")
	List<Product> selectProductNumListIn(@Param("productNum") List<String> list) throws Exception;

	/**
	 * 상품 재고량 업데이트
	 * @param product
	 * @throws Exception
	 */
	@Modifying
	@Query("update Product p set p.cnt =:#{#product.cnt} where p.productNum =:#{#product.productNum}")
	void updateProductCnt(@Param("product") Product product) throws Exception;
}
