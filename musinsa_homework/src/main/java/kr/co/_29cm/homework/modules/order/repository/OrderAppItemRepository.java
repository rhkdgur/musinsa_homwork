package kr.co._29cm.homework.modules.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co._29cm.homework.modules.order.entity.OrderAppItem;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.order.repository
* @fileName      : OrderAppItemRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 상품 아이템 목록 레포지토리
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       최초 생성
 */
public interface OrderAppItemRepository extends JpaRepository<OrderAppItem, Integer>{

}
