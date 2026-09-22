package com.ktdsuniversity.edu.articles.service;

import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;

public interface ArticlesService {
	
	/**
	 * 게시글의 목록을 조회.
	 * @return (게시글 개수, 게시글 목록)
	 */
	ArticleListVO readAllAricles();

}
