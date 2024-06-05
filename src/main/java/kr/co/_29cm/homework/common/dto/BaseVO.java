package kr.co._29cm.homework.common.dto;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 
* @packageName   : kr.co._29cm.homework.common.dto
* @fileName      : BaseVO.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 페이징 및 검색 공통VO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       	최초생성
 */
public class BaseVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**통합검색*/
	private String sstring = "";
	
	/**검색 구분*/
	private String stype = "";
	
	/**현재 페이지*/
	private int page = 1;
	
	/**페이지 목록 개수*/
	private int size = 10;
	
	private Sort sort = null;
	
	public Pageable getPageable() {
		if(sort != null) {
			return PageRequest.of((this.page - 1),this.size,sort);
		}
		return PageRequest.of((this.page - 1),this.size);
	}

	public String getSstring() {
		return sstring;
	}

	public void setSstring(String sstring) {
		this.sstring = sstring;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}
	
	
}
