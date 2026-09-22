package com.ktdsuniversity.edu.articles.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.articles.dao.ArticlesDao;
import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ArticlesServiceimpl implements ArticlesService{
	
	private ArticlesDao articlesDao;
	
	
//	@AllArgsConstructor
//	=
//	public ArticlesServiceimpl(ArticlesDao articlesDao) {
//		this.articlesDao = articlesDao;
//	}



	@Override
	public ArticleListVO readAllAricles() {

		
		long count = this.articlesDao.selectArticlesCount();
		List<ArticlesVO> articlesList = this.articlesDao.selectAllArticles();
		
		ArticleListVO list  = new ArticleListVO();
		list.setArticleCount(count);
		list.setArticleList(articlesList);
		
		return list;
	}


	@Override
	public ArticlesVO createNewArticle(RegistArticleVO registArticleVO) {
		
		
		int insertedRows = this.articlesDao.inserNewArticle(registArticleVO);
		
		// insert한 게시글의 ID로 게시글 정보를 조회한다.
		// -> insert한 게시글의 ID가 뭔지 모른다.
		
		System.out.println(insertedRows + "개의 row가 생성되었습니다.");
		
		if(insertedRows > 0) {
			return this.articlesDao.selectArticleByArticleId(registArticleVO.getId());
		}
		
		throw new IllegalArgumentException("입력값이 유효하지 않습니다.");
	}


	@Override
	public ArticlesVO updateArticle(String articleId, ModifyArticleVO modifyArticleVO) {
		int updatedRows = this.articlesDao.updateArticle(articleId, modifyArticleVO);
		
		if(updatedRows == 0) {
			throw new IllegalArgumentException("존재하지 않는 게시글 입니다.");
		}
		return this.articlesDao.selectArticleByArticleId(articleId);
	}


	@Override
	public String deleteArticle(String articleId) {
		int delete = this.articlesDao.deleteArticle(articleId);
		if(delete == 0) {
			throw new IllegalArgumentException("존재하지않는 게시글 입니다");
		}
		return this.articlesDao.deleteArticleByArticleId(articleId);
	}


	@Override
	public ArticlesVO readOneArticle(String articleId) {
		
		int viewCount = this.articlesDao.updateIncreaseView(articleId);
		if(viewCount == 0) {
			throw new IllegalArgumentException("존재하지않는 게시글 입니다.");
		}
		return this.articlesDao.selectArticleByArticleId(articleId);
	}


	@Override
	public long recommendOneArticle(String articleId) {
		int recommendCount = this.articlesDao.updateIncreaseRecommendCount(articleId);
		if(recommendCount == 0) {
			throw new IllegalArgumentException("존재하지않는 게시글 입니다.");
		}
		return this.articlesDao.count(articleId);
	}

}
