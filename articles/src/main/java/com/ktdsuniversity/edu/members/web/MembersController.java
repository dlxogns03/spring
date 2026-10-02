package com.ktdsuniversity.edu.members.web;

import com.ktdsuniversity.edu.articles.service.ArticlesServiceimpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.members.service.MembersService;
import com.ktdsuniversity.edu.members.vo.request.LoginMemberVO;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RestController
public class MembersController {
	
	
	private MembersService membersService;

//	MembersController(ArticlesServiceimpl articlesServiceimpl) {
//		this.articlesServiceimpl = articlesServiceimpl;
//	}
	
	@PostMapping("/members")
	public ApiResponse<MembersVO> createNewMember(
												 @Valid @RequestBody RegistMembersVO registMembersVO,
												 BindingResult validationResults){
		
		if(validationResults.hasErrors()) {
			return ApiResponse.BAD_REQUEST(validationResults.getFieldErrors());
		}
		
		try {
			MembersVO member = this.membersService.createNewMember(registMembersVO);
			return ApiResponse.OK(member);
			
		}catch(IllegalArgumentException iae){
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
		
		
		
	}
	
	
	@GetMapping("/members/login")
	public ApiResponse<MembersVO> loginMember( @Valid @ModelAttribute LoginMemberVO loginMemberVO
							, BindingResult validationResult
							, HttpSession session) {
		
		// AC6E49663F074668CEDB35D19863BFF1<-- SessionID
		System.out.println(session.getId() + "<-- SessionID");
		
		if (validationResult.hasErrors()) {
			
			return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
		
		}
		
		try {
			MembersVO loggedMember = this.membersService.readMember(loginMemberVO);
			
			//httpSession에 로그인 한 사용자의 정보를 기억시킨다. __LOGIN_USER__라는 KEY값에 LOGGEDMEMBER의 값을 넣어준다.
			session.setAttribute("__LOGIN_USER__", loggedMember);
			return ApiResponse.OK(loggedMember);
		}catch( IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
		
		
		
	}
	
	@GetMapping("/members/logout")
	public ApiResponse<String> logout (HttpSession session){
		MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
		if (membersVO == null) {
			throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
		}
		
		//session 만료처리.
		// 만료된 session의 id는 더이상 사용할 수 없음.
		session.invalidate();
		String email = this.membersService.updateLogoutStatus(membersVO.getEmail());
		
		return ApiResponse.OK(email);
	}
	
	@GetMapping("/members/exit")
	public ApiResponse<String> exitMember(HttpSession session,
										  String password){
		MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
		if (membersVO == null) {
			throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
		}
		
		try {
			String email = this.membersService.deleteMember(membersVO.getEmail(), password);
			return ApiResponse.OK(email);
		}catch(IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
		
		
		
	}
}
