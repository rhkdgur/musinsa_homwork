package kr.co._29cm.homework.modules.order.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co._29cm.homework.common.service.BaseServiceImpl;
import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppDefaultDTO;
import kr.co._29cm.homework.modules.order.entity.OrderApp;
import kr.co._29cm.homework.modules.order.entity.QOrderApp;
import kr.co._29cm.homework.modules.order.repository.OrderAppCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : kr.co._29cm.homework.modules.order.repository.impl
 * fileName       : OrderAppCustomRepositoryImpl
 * author         : rhkdg
 * date           : 2024-06-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-07        rhkdg       최초 생성
 */
@Repository
public class OrderAppCustomRepositoryImpl extends BaseServiceImpl implements OrderAppCustomRepository {
    public OrderAppCustomRepositoryImpl(JPAQueryFactory jpaQuery) {
        super(jpaQuery);
    }

    /**
     * 검색 공통 쿼리
     * @param searchDTO
     * @return
     */
    BooleanBuilder commonQuery(OrderAppDefaultDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();
        QOrderApp qOrderApp = QOrderApp.orderApp;

        //주문번호
        if(searchDTO.getOrderNum() != null && !searchDTO.getOrderNum().isEmpty()) {
            builder.and(qOrderApp.orderNum.eq(searchDTO.getOrderNum()));
        }

        return builder;
    }

    @Override
    public Page<OrderAppDTO> selectOrderAppPageList(OrderAppDefaultDTO searchDTO) throws Exception {

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

    @Override
    public OrderAppDTO selectOrderApp(OrderAppDTO dto) throws Exception {
        return null;
    }
}
