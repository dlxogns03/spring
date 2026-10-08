package com.ktdsuniversity.edu.articles.vo.request;

import com.ktdsuniversity.edu.commons.vo.PaginationVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 게시글 검색을 위한 VO
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchArticleVO extends PaginationVO{
	/**
	 * 검색을 위한 url /articles/list?pageNo=0&listSize=10&subject=제목&name=작성자
	 * 조건에 따라서 쿼리를 검색하는 것 => Dynamic query
	 * 1. chose ~ when ~ otherwise
	 * 2. if 
	 */
	private String subject;
	private String content;
	private String fileName;
	private String name;
	private String nickname;
}
