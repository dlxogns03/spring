package com.ktdsuniversity.edu.replies.vo.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistRepliesVO {
	private String id;
	private String articleId;
	
	private String email;
	
	private String content;

	
	private List<MultipartFile> file;
	private String fileSetId;
}
