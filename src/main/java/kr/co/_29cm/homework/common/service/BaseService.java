package kr.co._29cm.homework.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * 
* @packageName   : kr.co._29cm.homework.common.service
* @fileName      : BaseService.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 공통 service 기능  
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       		최초생성
 */
@Component
public class BaseService {
	/**QueryDSL*/
	@Autowired
	protected JPAQueryFactory jpaQuery;
}
