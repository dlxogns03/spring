package com.ktdsuniversity.edu.articles.vo.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ModifyArticleVO {
	
	@NotBlank(message = "공백은 입력하실수 없습니다.")
	@Size(min = 2, message="제목은 두 글자 이상 입력해주세요")
	private String subject;
	
	private String content;
	private String email;
	
	private List<MultipartFile> file;
	private String fileSetId;
	
}
