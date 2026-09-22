package com.ktdsuniversity.edu.articles.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;

/**
 *  Spring의 @Repository를 한번 감싼 애노테이션.
 *  마이바티스가 익명의 클래스를 만들어서 데이터베이스에 접근하도록 한다.
 */
@Mapper
public interface ArticlesDao {
	/**
	 * 게시글의 총 개수를 반환
	 * @return
	 */
	long selectArticlesCount();
	
	/**
	 * 게시글을 검색해서 반환 
	 * @return
	 */
	List<ArticlesVO> selectAllArticles();

	/**
	 * 클라이언트가 보내준 게시글 등록 정보를 데이터 베이스에 insert 한다.
	 * @param registArticleVO
	 * @return insert 한 row의 개수
	 */
	int inserNewArticle(RegistArticleVO registArticleVO);
	
	/**
	 * 게시글의 아이디로 게시글 정보를 조회한다
	 * @param articleId 게시글의 pk
	 * @return 게시글의 pk로 조회한 게시글 정보
	 */
	ArticlesVO selectArticleByArticleId(String articleId);

	/**
	 * 게시글의 아이디로 게시글을 조회하여 수정한다.
	 * @param articleId 
	 * @param modifyArticleVO
	 * @return
	 */
	int updateArticle(
			@Param("articleId") String articleId,
			@Param("modifyArticleVO") ModifyArticleVO modifyArticleVO);

	/**
	 * 게시글의 아이디로 게시글을 삭제한다
	 * @param articleId
	 * @return
	 */
	int deleteArticle(@Param("articleId")String articleId);

	/**
	 * 삭제한 게시글 아이디 출력 
	 * @param articleId
	 * @return
	 */
	String deleteArticleByArticleId(String articleId);
	
	
	/**
	 * 조회한 게시글 view수 증가 
	 * @param articleId
	 * @return
	 */
	int updateIncreaseView(String articleId);

	int updateIncreaseRecommendCount(String articleId);
	
	long count(String articleId);
}
