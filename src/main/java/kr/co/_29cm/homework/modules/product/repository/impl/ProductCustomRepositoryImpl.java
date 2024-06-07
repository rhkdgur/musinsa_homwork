package kr.co._29cm.homework.modules.product.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co._29cm.homework.common.service.BaseServiceImpl;
import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import kr.co._29cm.homework.modules.product.dto.ProductDefaultDTO;
import kr.co._29cm.homework.modules.product.entity.Product;
import kr.co._29cm.homework.modules.product.entity.QProduct;
import kr.co._29cm.homework.modules.product.repository.ProductCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : kr.co._29cm.homework.modules.product.repository.impl
 * fileName       : ProductCustomRepositoryImpl
 * author         : rhkdg
 * date           : 2024-06-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-07        rhkdg       최초 생성
 */
@Repository
public class ProductCustomRepositoryImpl extends BaseServiceImpl implements ProductCustomRepository {
    public ProductCustomRepositoryImpl(JPAQueryFactory jpaQuery) {
        super(jpaQuery);
    }

    // 공통 쿼리 메소드
    BooleanBuilder commonQuery(ProductDefaultDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();
        QProduct qProduct = QProduct.product;

        if(searchDTO.getProductNum() != null && !searchDTO.getProductNum().isEmpty()) {
            builder.and(qProduct.productNum.eq(searchDTO.getProductNum()));
        }

        return builder;
    }

    @Override
    public Page<ProductDTO> selectProductPageList(ProductDefaultDTO searchDTO) throws Exception {

        QProduct qProduct = QProduct.product;

        //상품 개수
        Long cnt = jpaQuery.select(qProduct.count()).from(qProduct)
                .where(commonQuery(searchDTO)).fetchFirst();

        //상품 목록
        List<Product> list = jpaQuery.selectFrom(qProduct)
                .where(commonQuery(searchDTO))
                .offset(searchDTO.getPageable().getOffset())
                .limit(searchDTO.getPageable().getPageSize()).fetch();

        return new PageImpl<>(list.stream().map(ProductDTO::new).collect(Collectors.toList()),searchDTO.getPageable(),cnt);
    }
}
