package com.ktdsuniversity.edu.articles.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;

@Controller
public class ArticlesController {
	
	@GetMapping("/articles")
	// 컨트롤러가 반환 시키는 "객체"를 "JSON"으로 변환시키는 View를 사용해라! ==> @ResponseBody
	@ResponseBody
	public ArticleListVO getArticles() {
		
		ArticleListVO list = new ArticleListVO();
		
		list.setArticleCount(1000);
		
		List<ArticlesVO> articles = new ArrayList<>();
		articles.add(new ArticlesVO());
		articles.add(new ArticlesVO());
		articles.add(new ArticlesVO());
		
		list.setArticleList(articles);
		
		
		return list;
	}
}
