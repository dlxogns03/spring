package com.ktdsuniversity.edu.commons.exceptions.web;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktdsuniversity.edu.commons.util.ApiResponse;

@RestController
public class ErrorResponseController implements ErrorController{
	
	@GetMapping("/error")
	public ApiResponse<String> sendErrorPage(){
		return ApiResponse.ERROR("존재하지 않는 url입니다.");
	}
}
