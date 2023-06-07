package kr.co._29cm.homework.common.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * 
* @packageName   : kr.co._29cm.homework.common.service
* @fileName      : BaseService.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : ���� ���� ����
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       ���� ����
 */
@Component
public class BaseService {
	/**QueryDSL ����*/
	@Autowired
	protected JPAQueryFactory jpaQuery;
}
