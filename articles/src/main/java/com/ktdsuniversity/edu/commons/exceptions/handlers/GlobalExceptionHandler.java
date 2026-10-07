package com.ktdsuniversity.edu.commons.exceptions.handlers;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.ktdsuniversity.edu.commons.exceptions.ArticleException;
import com.ktdsuniversity.edu.commons.exceptions.Messages;
import com.ktdsuniversity.edu.commons.exceptions.enums.ArticleCodes;
import com.ktdsuniversity.edu.commons.exceptions.enums.ExceptionType;
import com.ktdsuniversity.edu.commons.util.ApiResponse;

/**
 * Programming 용어 : GOLBAL ==> 전역 
 * --> throw 되는 예외들마다 엔드포인트를 생성.
 */

@RestControllerAdvice //throw되는 예외들마다 엔드포인트를 생성.
public class GlobalExceptionHandler {
	
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	//ArticleException이 throw 되면 아래 메소드가 실행된다.
	//이 한줄로 모든 코드의 try catch를 생략할수 있다.
	@ExceptionHandler(ArticleException.class)
	public ApiResponse<String> sendArticleExceptionMessage(ArticleException ae){
		
		ExceptionType type = ae.getType();
		ArticleCodes code = ae.getCodes();
		
		String message = Messages.getMessage(type, code);
		
		return ApiResponse.FORBIDDEN(message);
	}
	
	@ExceptionHandler(HandlerMethodValidationException.class)
	public ApiResponse<String> sendValidationExceptionMessage(HandlerMethodValidationException hmve){
		
	 	List<? extends MessageSourceResolvable> errors = hmve.getAllErrors();
		
		return ApiResponse.BAD_REQUEST(errors);
	}
	
	
	@ExceptionHandler(RuntimeException.class)
	public ApiResponse<String> sendRuntimeExceptionMessage(RuntimeException re){
		
		logger.error(re.getMessage(), re);
		
		return ApiResponse.ERROR("요청을 처리할 수 없습니다. 잠시 후 다시 시도해주세요.");
	}
}
