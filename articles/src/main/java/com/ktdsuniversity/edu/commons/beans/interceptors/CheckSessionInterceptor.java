package com.ktdsuniversity.edu.commons.beans.interceptors;

import java.io.PrintWriter;

import org.springframework.web.servlet.HandlerInterceptor;

import com.google.gson.Gson;
import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CheckSessionInterceptor implements HandlerInterceptor {
	/**
	 * 컨트롤러가 modelandView 반환
	 * 
	 * 1.preHandle -> 컨트롤러 실행전 (boolean) 2.postHandle -> 컨트롤러가 반환한 ModelAndView
	 * (void) 3,afterCompletion -> Response를 가로챈다.(void)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 세션을 검사하고 
		HttpSession session = request.getSession();
		MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
		
		
		// 세션이 있으면 컨트롤러를 실행.
		if(membersVO != null) {
			return true;
		}else {
			
			//Response의 Content-Type을 JSON (application/json)으로 설정
			response.setContentType("application/json");
			
			//클라이언트가 표현할 인코딩을 UTF-8로 설정
			response.setCharacterEncoding("UTF-8");
			
			// 클라이언트에게 응답메세지를 직접 전달할 수 있는 객체
			// Servlet Code를 작성할때에 필수 코드.
			PrintWriter printWriter =  response.getWriter();
			
			ApiResponse<String> errorResponse = ApiResponse.FORBIDDEN("로그인이 필요한 기능입니다");
			// errorResponse ==> JSON으로 변환! (Jackson Databind ==> @ResponseBody로 변환할때 사용되는 라이브러리
			//								 , Gson ==> Jackson Databind보다 느리다.)
			Gson gson = new Gson();
			String errorJson = gson.toJson(errorResponse);
			
			//printWriter에게 write
			printWriter.write(errorJson);
			
//			printWriter.write("{ json 메세지 직접 작성 }");
			
			
			//printWriter에 작성한 내용들이 클라이언트에게 전달된다.
			printWriter.flush();
			
			// 세션이 없으면 컨트롤러를 실행 X ==> 클라이언트에게 예외 메세지를 전달.-> 직접 클라이언트에게 전달
			
			return false;
		}
	}
}
