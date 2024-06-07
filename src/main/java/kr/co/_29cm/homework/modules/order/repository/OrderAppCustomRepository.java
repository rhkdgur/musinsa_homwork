package kr.co._29cm.homework.modules.order.repository;

import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppDefaultDTO;
import org.springframework.data.domain.Page;

/**
 * packageName    : kr.co._29cm.homework.modules.order.repository
 * fileName       : OrderAppCustomRepository
 * author         : rhkdg
 * date           : 2024-06-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-07        rhkdg       최초 생성
 */
public interface OrderAppCustomRepository {
    
    /**주문 정보 목록*/
    Page<OrderAppDTO> selectOrderAppPageList(OrderAppDefaultDTO searchDTO) throws Exception;
    
    /**주문 상세 정보*/
    OrderAppDTO selectOrderApp(OrderAppDTO dto) throws Exception;
    
}
