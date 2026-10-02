package com.ktdsuniversity.edu.files.components;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.RequestFileSetVO;
import com.ktdsuniversity.edu.files.vo.request.RequestFileVO;
import com.ktdsuniversity.edu.files.vo.response.FilesVO;

import lombok.AllArgsConstructor;

//@AllArgsConstructor
@Component
public class MultipartHandler {

	@Value("${app.multipart.store-path}")
	private String uploadFolderName;
	
	private FilesDao filesDao;
	
	public MultipartHandler(FilesDao filesdao) {
		this.filesDao = filesdao;
	}
	
	/**
	 * 파일을 업로드하고 FILE_SET_ID를 발급한다.
	 * 
	 * @param file  첨부파일
	 * @param email 업로더 이메일
	 * @return FILE_SET_ID
	 */
	public String storeFiles(List<MultipartFile> file, String email) {
		return storeFiles(file, email, null);
	}

	/**
	 * 파일을 업로드하고 FILE_SET_ID를 반환한다.
	 * 
	 * @param file      첨부파일
	 * @param email     업로더 이메일
	 * @param fileSetId 기존의 FILE_SET_ID
	 * @return FILE_SET_ID
	 */
	public String storeFiles(List<MultipartFile> file, String email, String fileSetId) {

		if (file == null) {
			return fileSetId;
		}

		if (fileSetId == null) {
			// FILE_SET_ID 생성
			RequestFileSetVO fileSetVO = new RequestFileSetVO();
			fileSetVO.setEmail(email);
			int insertCount = this.filesDao.insertNewFileSet(fileSetVO);
			if (insertCount == 0) {
				throw new IllegalArgumentException("파일 세트를 만들 수 없습니다.");
			}

			fileSetId = fileSetVO.getId();
		}

		String homeDirectory = System.getProperty("user.home");
		File uploadPath = new File(homeDirectory, "uploadFiles");
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}

		File storeFile = null;
		RequestFileVO fileVO = null;

		for (MultipartFile multipartFile : file) {
			storeFile = new File(uploadPath, UUID.randomUUID().toString());
			try {
				multipartFile.transferTo(storeFile);

				fileVO = new RequestFileVO();
				fileVO.setFileSetId(fileSetId);
				fileVO.setDisplayFileName(multipartFile.getOriginalFilename());
				fileVO.setObfuscateFileName(storeFile.getName());
				fileVO.setFileSize(storeFile.length());

				this.filesDao.insertNewFile(fileVO);
			} catch (IllegalStateException | IOException e) {
				throw new IllegalArgumentException(e.getMessage(), e);
			}
		}

		return fileSetId;
	}
	
	public int deleteFiles(String fileSetId) {
		//파일의 물리적 삭제 진행.
		// FILES에서 FIEL_SET_ID를 이용해 파일의 난독화된 이름을 조회
		List<FilesVO> files = this.filesDao.selectFilesByFileSetId(fileSetId);
		
		
		//조회된 파일의 이름으로 파일 물리적 삭제 진행
		//파일이 저장되어 있는 위치정보 필요 
		String homeDirectory = System.getProperty("user.home");
		File uploadPath = new File(homeDirectory, this.uploadFolderName);
		
		for(FilesVO file : files) {
			new File(uploadPath, file.getObfuscateFileName())	
					.delete();
		}
		//fileSetId로 FILES 테이블의 DEL_YN을 Y로 변경한다.
		int deleteCount = this.filesDao.deletFilesbyFileSetId(fileSetId);
		
		return deleteCount;
	}

}
