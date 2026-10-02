package com.ktdsuniversity.edu.replies.vo.resqonse;

import lombok.Data;

@Data
public class RepliesVO {
	private String id;
	private String articleId;
	private String email;
	private String content;
	private long recommendCnt;
	private String delYn;
	private String crtDt;
	private String mdfyDt;
	private String fileSetId;
}
