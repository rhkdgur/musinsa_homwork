package kr.co._29cm.homework;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : kr.co._29cm.homework
* @fileName      : AppConfig.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 쿼리 라이브러리 bean 처리 클래스
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       최초 생성
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {
	
	private final EntityManager em;

    @Bean
    public JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(em);
    }

}
