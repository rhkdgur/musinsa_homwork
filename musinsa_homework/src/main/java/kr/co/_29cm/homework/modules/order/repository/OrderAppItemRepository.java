package kr.co._29cm.homework.modules.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co._29cm.homework.modules.order.entity.OrderAppItem;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.order.repository
* @fileName      : OrderAppItemRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 주문 상품 repository
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       		최초생성
 */
public interface OrderAppItemRepository extends JpaRepository<OrderAppItem, Integer>{

}
