package com.example.demo.home.articles.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.home.articles.service.ArticlesService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ArticlesController {
	private ArticlesService articlesService;
	
	@GetMapping("/articles")
	public void asdf() {
		this.articlesService.makeArticles();
	}
}
