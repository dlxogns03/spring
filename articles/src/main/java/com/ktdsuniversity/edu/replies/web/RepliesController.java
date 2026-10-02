package com.ktdsuniversity.edu.replies.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import com.ktdsuniversity.edu.replies.service.RepliesService;
import com.ktdsuniversity.edu.replies.vo.request.ModifyRepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.resqonse.RepliesListVO;
import com.ktdsuniversity.edu.replies.vo.resqonse.RepliesVO;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController //(@Controller + @ResponseBody) 메소드에 @ResponseBody 생략 가능
public class RepliesController {
	
	
	private RepliesService repliesService;
	//GET / replies/{게시글아이디}
	//게시글에 등록된 댓글 반환.
	@GetMapping("/articles/{articleId}/replies")
	public ApiResponse<RepliesListVO> getReplies(@PathVariable String articleId){
		RepliesListVO result = this.repliesService.readAllReplies(articleId);
		return ApiResponse.OK(result);
	}
	
	//POST / replies/{게시글아이디}
	//게시글에 댓글 작성(파일첨부 가능)
	///articles/AR-20261002-000046/replies
	@PostMapping("/articles/{articleId}/replies")
	public ApiResponse<RepliesVO> makeNewReplies(
									@Size(min = 18, max=20, message="잘못된 값입니다.")
									@PathVariable String articleId,
									@Valid @ModelAttribute RegistRepliesVO registRepliesVO,
									BindingResult validationResult,
									HttpSession session){
		
		if(validationResult.hasErrors()) {
			return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
		}
		
		//HttpSession에 있는 __LOGIN_USER__에 있는 EAMIL을 꺼내 REGISTARTICLEVO에 할당.
		MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
		if(membersVO == null) {
			throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
		}
		registRepliesVO.setEmail( membersVO.getEmail());
		
		
		try {
			RepliesVO replies = this.repliesService.createNewReplies(articleId, registRepliesVO);
			return ApiResponse.CREATED(replies);
		} catch(IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
	}
	//PUT /replies/{게시글아이디}/{댓글아이디}
	//게시글에 등록된 댓글을 수정(파일 첨부 가능)
	@PutMapping("/articles/{articleId}/replies/{replyId}")
	public ApiResponse<RepliesVO> updateReplies(
												@Size(min = 18, max=20, message="잘못된 값입니다.")
												@PathVariable String articleId,
												
												@Size(min = 18, max=20, message="잘못된 값입니다.")
												@PathVariable("replyId") String repliesId,
												
												@Valid @ModelAttribute ModifyRepliesVO modifyRepliesVO,
												BindingResult vaildationResult,
												HttpSession session){
		
		
		
		if(vaildationResult.hasErrors()) {
			return ApiResponse.BAD_REQUEST(vaildationResult.getFieldErrors());
		}
		
		//HttpSession에 있는 __LOGIN_USER__에 있는 EAMIL을 꺼내 REGISTARTICLEVO에 할당.
		MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
		if(membersVO == null) {
			throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
		}
		modifyRepliesVO.setEmail( membersVO.getEmail());
		try {
			RepliesVO modifyrelies = this.repliesService.updateReplies(articleId, repliesId, modifyRepliesVO);
			return ApiResponse.OK(modifyrelies);
		} catch(IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
		
	}
	
	
	//DELETE /replies/{게시글아이디}/{댓글아이디}
	//게시글에 등록된 댓글 하나를 삭제
	// 첨부된 파일 제거 
	@DeleteMapping("/articles/{articleId}/replies/{replyId}")
	public ApiResponse<String> deleteOneReplies(
												@Size(min = 18, max=20, message="잘못된 값입니다.")
												@PathVariable String articleId,
												
												@Size(min = 18, max=20, message="잘못된 값입니다.")
												@PathVariable("replyId") String repliesId){
		try {
			String result = this.repliesService.deleteReplies(articleId, repliesId);
			return ApiResponse.OK(result);
		} catch(IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
	}
	
	// PUT/replies/{게시글아이디}/recommend/{댓글아이디}
	//게시글에 등록된 댓글 하나를 추천.
	@PutMapping("/articles/{articleId}/replies/recommend/{replyId}")
	public ApiResponse<Long> recommendOneReplies(
												 @Size(min = 18, max=20, message="잘못된 값입니다.")
												 @PathVariable String articleId,
												 
												 @Size(min = 18, max=20, message="잘못된 값입니다.")
												 @PathVariable("replyId") String repliesId){
		
		try {
			long recommendCount = this.repliesService.recommendReplies(articleId, repliesId);
			return ApiResponse.OK(recommendCount);
		}catch(IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
		
	}
}
