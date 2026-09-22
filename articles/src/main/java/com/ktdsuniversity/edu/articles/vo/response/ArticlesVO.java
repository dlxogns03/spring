package com.ktdsuniversity.edu.articles.vo.response;

import lombok.Data;


/**
 * 
 */
//@Getter //멤버면수들의 Getter를 자동생성.
//@Setter //멤버면수들의 Setter를 자동생성.
//@ToString //toString 메소드를 자동생성.
//@AllArgsConstructor// 모든 멤버변수들을 파라미터로 가지는 생성자 자동생성.
//@NoArgsConstructor // 기본 생성자를 자동생성
@Data // @Getter, @Setter, @ToString 자동생성.
//@AllArgsConstructor
//@NoArgsConstructor
public class ArticlesVO {

	private String id;
	private String subject;
	private String content;
	private String email;
	private long viewCnt;
	private long recommendCnt;
	private String delYn;
	private String crtDt;
	private String mdfyDt;
	private String fileSetId;

}
