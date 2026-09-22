package com.ktdsuniversity.edu.articles.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.articles.dao.ArticlesDao;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;

@Service
public class ArticlesServiceimpl implements ArticlesService{
	
	private ArticlesDao articlesDao;
	
	
	public ArticlesServiceimpl(ArticlesDao articlesDao) {
		this.articlesDao = articlesDao;
	}



	@Override
	public ArticleListVO readAllAricles() {
		
		long count = this.articlesDao.selectArticlesCount();
		List<ArticlesVO> articlesList = this.articlesDao.selectAllArticles();
		
		ArticleListVO list  = new ArticleListVO();
		list.setArticleCount(count);
		list.setArticleList(articlesList);
		
		return list;
	}

}
