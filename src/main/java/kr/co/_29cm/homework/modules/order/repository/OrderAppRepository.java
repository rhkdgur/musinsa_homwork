package kr.co._29cm.homework.modules.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co._29cm.homework.modules.order.entity.OrderApp;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.order.repository
* @fileName      : OrderAppRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 주문 정보 repository
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       		최초생성
 */
public interface OrderAppRepository extends JpaRepository<OrderApp, String>, OrderAppCustomRepository{
	
	/***
	 * �ֹ���ȣ ����
	 * @return
	 */
	@Query(value=" select CONVERT(IFNULL(MAX(order_num),concat(FORMATDATETIME(NOW(),'yyyyMMdd'),'00')),int)+1 as orderNum  FROM order_app"
			+ "	where order_num like concat(FORMATDATETIME(NOW(),'yyyyMMdd'),'%')",nativeQuery = true)
	String selectOrderNumMax() throws Exception;
	
	/**
	 * �ֹ� ���� ��ȸ
	 * @param orderNum
	 * @return
	 * @throws Exception
	 */
	@EntityGraph(attributePaths = "itemList",type = EntityGraph.EntityGraphType.LOAD)
	@Query("select o from OrderApp o where o.orderNum=:orderNum")
	List<OrderApp> selectOrderAppList(@Param("orderNum") String orderNum) throws Exception;
}
