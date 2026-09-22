package com.ktdsuniversity.edu.articles.service;

import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;

public interface ArticlesService {
	
	/**
	 * 게시글의 목록을 조회.
	 * @return (게시글 개수, 게시글 목록)
	 */
	ArticleListVO readAllAricles();

	/**
	 * 게시글 생성
	 * @param registArticleVO클라이언트가 보내준 게시글 등록 정보 (제목,내용,이메일)
	 */
	ArticlesVO createNewArticle(RegistArticleVO RegistArticleVO);

	/**
	 * 게시글 수정 
	 * @param articleId 수정하려는 게시글의 아이디
	 * @param modifyArticleVO 수정하려는 내용
	 * @return 수정된 결과 
	 */
	ArticlesVO updateArticle(String articleId, ModifyArticleVO modifyArticleVO);

	/**
	 * 게시글 삭제 
	 * @param articleId 삭제하려는 게시글 아이디
	 * @return 
	 */
	String deleteArticle(String articleId);

	ArticlesVO readOneArticle(String articleId);

	long recommendOneArticle(String articleId);
	
	
	
}
