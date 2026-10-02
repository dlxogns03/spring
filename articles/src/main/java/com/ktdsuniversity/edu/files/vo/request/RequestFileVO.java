package com.ktdsuniversity.edu.files.vo.request;

import lombok.Data;

@Data
public class RequestFileVO {
	
	private String fileSetId;
	private String displayFileName;
	private String obfuscateFileName;
	private long fileSize;
}
