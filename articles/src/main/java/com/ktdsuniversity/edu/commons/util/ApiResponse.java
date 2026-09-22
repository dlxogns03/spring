package com.ktdsuniversity.edu.commons.util;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {
	
	private int httpStatusCode;
	private String httpStatusMessage;
	private T body;

	private String error;
	
	
	public static <T> ApiResponse<T> OK(T t){
		ApiResponse<T> result = new ApiResponse<>();
		result.setHttpStatusCode(HttpStatus.OK.value());
		result.setHttpStatusMessage(HttpStatus.OK.getReasonPhrase());
		result.setBody(t);
		
		return result;
		
	}
	
	public static <T> ApiResponse<T> CREATED(T t){
		ApiResponse<T> result = new ApiResponse<>();
		result.setHttpStatusCode(HttpStatus.CREATED.value());
		result.setHttpStatusMessage(HttpStatus.CREATED.getReasonPhrase());
		result.setBody(t);
		
		return result;
	}
	
	public static <T> ApiResponse<T> ERROR(String message){
		ApiResponse<T> result = new ApiResponse<>();
		result.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		result.setHttpStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		result.setError(message);
		
		return result;
	}
	
	public static <T> ApiResponse<T> FORBIDDEN(String message){
		ApiResponse<T> result = new ApiResponse<>();
		result.setHttpStatusCode(HttpStatus.FORBIDDEN.value());
		result.setHttpStatusMessage(HttpStatus.FORBIDDEN.getReasonPhrase());
		result.setError(message);
		
		return result;
	}
}
