package com.ktdsuniversity.edu.articles.web;


import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.articles.service.ArticlesService;
import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;
import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class ArticlesController {
	
//	/**
//	 *@Autowired ==> BeanContainer에서 같은 타입의 객체가 있다면, 그것을 멤버변수에게 할당시켜라
//	 */
//	@Autowired
//	/**
//	 * @Qualifier("articlesServiceimpl")
//	 * BeanContainer에 ArticlesService 타입의 객체가 여러개 있을경우
//	 * 객체 명이 "articlesServiceimpl"인 객체를 멤버변수에 할당시켜라
//	 */
//	@Qualifier("articlesServiceimpl")
	private ArticlesService articlesService;
	
	/**
	 * Spring Framework 7.0이상
	 * Spring Boot 4.0 이상에서는 @Autowired 사용을 권장하지 않는다.
	 * 
	 * 대신, 생성자를 이용한 DI를 권장한다.
	 * ==> 이유 : Lombok Library때문...(Getter,Setter, 생성자, toString 자동생성)
	 */
//	public ArticlesController(ArticlesService articlesService) {
//		this.articlesService = articlesService;
//	}
	
	@GetMapping("/articles")
	// 컨트롤러가 반환 시키는 "객체"를 "JSON"으로 변환시키는 View를 사용해라! ==> @ResponseBody
	@ResponseBody
	public ApiResponse<ArticleListVO> getArticles() {
//		System.out.println(this.articlesService);
		ArticleListVO result = this.articlesService.readAllAricles();
		return ApiResponse.OK(result);
	}
	
	
	@PostMapping("/articles")  
	@ResponseBody              		
	public ApiResponse<ArticlesVO> makeNewArticle( 
			//command object
			//파일을 전송하는 방법
//두번째 방법	//클라이언트가 컨트롤러로 전송한 파라미터(폼파라미터, 쿼리 스트링파라미터(? K = V))를 자동으로 받아오는 역할.
			@Valid @ModelAttribute RegistArticleVO registArticleVO,
			BindingResult validationResult,
			//클라이언트가 컨트롤러로 전송한 파라미터(폼파라미터, 쿼리스트링파라미터)를 하나씩 받아오는 역할.
//첫번째 방법 	@RequestParam List<MultipartFile> file //multipartFile은 하나의 파일만 받아온다 => 
												   //LIST로 변경하면 복수개의 파일을 받아올 수 있다
			HttpSession session) {
		
		System.out.println(validationResult);
		
		// Validation 검사를 통과하지 못했다면
		if(validationResult.hasErrors()) {
			// 클라이언트에게 에러를 전달한다.
			return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
		}
		
		//HttpSession에 있는 __LOGIN_USER__에 있는 EAMIL을 꺼내 REGISTARTICLEVO에 할당.
		MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
		if(membersVO == null) {
			throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
		}
		registArticleVO.setEmail( membersVO.getEmail());
		
		try {
			ArticlesVO result = this.articlesService.createNewArticle(registArticleVO);
			
			return ApiResponse.CREATED(result);
		} catch(IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
		
	}
	
	@PutMapping("/articles/{articleId}")
	@ResponseBody
	public ApiResponse<ArticlesVO> updateArticle( 
				@PathVariable String articleId, 
				@Valid @ModelAttribute ModifyArticleVO modifyArticleVO,
				BindingResult validationResult,
				HttpSession session) {
		
		if(validationResult.hasErrors()) {
			return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
		}
		
		//HttpSession에 있는 __LOGIN_USER__에 있는 EAMIL을 꺼내 REGISTARTICLEVO에 할당.
		MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
		if(membersVO == null) {
			throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
		}
		modifyArticleVO.setEmail( membersVO.getEmail());
		
		try {
			ArticlesVO result = this.articlesService.updateArticle(articleId, modifyArticleVO);
			return ApiResponse.OK(result);
		}catch(IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
		
	}
	
	@DeleteMapping("/articles/{articleId}")
	@ResponseBody
	public ApiResponse<String> deleteArticle(
											@Size(min = 18, max=20, message="잘못된 값입니다.")
											@PathVariable String articleId) {
		
		
		try {
			String deleteResult = this.articlesService.deleteArticle(articleId);
			return ApiResponse.OK(deleteResult);
		}catch(IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
		
	}
	
	@GetMapping("/articles/{articleId}")
	@ResponseBody
	public ApiResponse<ArticlesVO> getOneArticle(@PathVariable String articleId) {
		try {
			ArticlesVO result = this.articlesService.readOneArticle(articleId);
			return ApiResponse.OK(result);
		}catch(IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
		
	}
	
	
	@PutMapping("/articles/recommend/{articleId}")
	@ResponseBody
	public ApiResponse<Long> recommendOneArticle(@PathVariable String articleId) {
		try {
			long recommendResult = this.articlesService.recommendOneArticle(articleId);
			return ApiResponse.OK(recommendResult);
			
		}catch(IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
		
	}
}
