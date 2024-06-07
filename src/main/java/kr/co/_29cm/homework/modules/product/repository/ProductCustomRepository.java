package kr.co._29cm.homework.modules.product.repository;

import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import kr.co._29cm.homework.modules.product.dto.ProductDefaultDTO;
import org.springframework.data.domain.Page;

/**
 * packageName    : kr.co._29cm.homework.modules.product.repository
 * fileName       : ProductCustomRepository
 * author         : rhkdg
 * date           : 2024-06-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-07        rhkdg       최초 생성
 */
public interface ProductCustomRepository {

    /**
     * 상품 목록(페이징 o)
     * @param searchDTO
     * @return
     * @throws Exception
     */
    Page<ProductDTO> selectProductPageList(ProductDefaultDTO searchDTO) throws Exception;

}
