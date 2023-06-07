package kr.co._29cm.homework.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co._29cm.homework.modules.product.entity.Product;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.product.repository
* @fileName      : ProductRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : ��ǰ �������丮
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       ���� ����
 */
public interface ProductRepository extends JpaRepository<Product, String>{

	/**
	 * ��ǰ ��� ������Ʈ
	 * @param product
	 * @throws Exception
	 */
	@Modifying
	@Query("update Product p set p.cnt =:#{#product.cnt} where p.productNum =:#{#product.productNum}")
	void updateProductCnt(@Param("product") Product product) throws Exception;
}
