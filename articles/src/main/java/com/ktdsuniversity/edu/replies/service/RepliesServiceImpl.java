package com.ktdsuniversity.edu.replies.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.articles.dao.ArticlesDao;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;
import com.ktdsuniversity.edu.files.components.MultipartHandler;
import com.ktdsuniversity.edu.replies.dao.RepliesDao;
import com.ktdsuniversity.edu.replies.vo.request.ModifyRepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.resqonse.RepliesListVO;
import com.ktdsuniversity.edu.replies.vo.resqonse.RepliesVO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RepliesServiceImpl implements RepliesService {

	private ArticlesDao articlesDao;
	private RepliesDao repliesDao;
	private MultipartHandler multipartHandler;

	@Override
	public RepliesListVO readAllReplies(String articleId) {

		List<RepliesVO> repliesList = this.repliesDao.readAllReplies(articleId);
		RepliesListVO list = new RepliesListVO();
		list.setRepliesList(repliesList);
		return list;
	}

	@Override
	public RepliesVO createNewReplies(String articleId, RegistRepliesVO registRepliesVO) {
		
		ArticlesVO article = this.articlesDao.selectArticleByArticleId(articleId);
		
		if (article == null) {
			throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
		}

		String fileSetId = this.multipartHandler.storeFiles(
															registRepliesVO.getFile(),
															registRepliesVO.getEmail());
		registRepliesVO.setFileSetId(fileSetId);
		registRepliesVO.setArticleId(articleId);
		int insertRow = this.repliesDao.insertNewReplies(registRepliesVO);
		if(insertRow > 0) {
			return this.repliesDao.selectRepliesByRepliesId(articleId, registRepliesVO.getId());
		}
		
		throw new IllegalArgumentException("입력값이 유효하지 않습니다.");
	}

	@Override
	public String deleteReplies(String articleId, String repliesId) {
		RepliesVO replies= this.repliesDao.selectRepliesByRepliesId(articleId,repliesId);
		
		
		int deleteCount = this.repliesDao.deleteOneReplies(articleId, repliesId);
		if(deleteCount == 0) {
			throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
		}
		int deletefile = this.multipartHandler.deleteFiles(replies.getFileSetId());
		System.out.println(deletefile +"개의 파일이 삭제되었습니다.");
		return replies.getId();
	}

	@Override
	public long recommendReplies(String articleId, String repliesId) {
		
		int result = this.repliesDao.recommendReplies(articleId, repliesId);
		if(result == 0) {
			throw new IllegalArgumentException("존재하지 않는 댓글 입니다.");
		}
		
		RepliesVO replies = this.repliesDao.selectRepliesByRepliesId(articleId, repliesId);
		return replies.getRecommendCnt();
	}

	@Override
	public RepliesVO updateReplies(String articleId,
									     String repliesId,
									     ModifyRepliesVO modifyRepliesVO) {
		
		RepliesVO replies = this.repliesDao.selectRepliesByRepliesId(articleId, repliesId);
		
		String fileSetId = this.multipartHandler.storeFiles(modifyRepliesVO.getFile(),
															modifyRepliesVO.getEmail(),
															replies.getFileSetId());
		modifyRepliesVO.setFileSetId(fileSetId);
		int updateRows = this.repliesDao.updateReplies(articleId, repliesId, modifyRepliesVO);
		System.out.println(updateRows + "=".repeat(40));
		if(updateRows == 0) {
			throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
		}
		
		return this.repliesDao.selectRepliesByRepliesId(articleId, repliesId);
	}

}
