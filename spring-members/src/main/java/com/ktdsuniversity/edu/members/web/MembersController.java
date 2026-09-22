package com.ktdsuniversity.edu.members.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.members.service.MemberService;
import com.ktdsuniversity.edu.members.vo.response.MembersListVO;

@Controller
public class MembersController {

	private MemberService memberService;
	
	public MembersController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/members")
	@ResponseBody
	public MembersListVO getMembers() {
		return this.memberService.readAllMembers();
	}
}
