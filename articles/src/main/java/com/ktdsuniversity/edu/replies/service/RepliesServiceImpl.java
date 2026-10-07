package com.ktdsuniversity.edu.replies.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.articles.dao.ArticlesDao;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;
import com.ktdsuniversity.edu.commons.exceptions.ArticleException;
import com.ktdsuniversity.edu.commons.exceptions.enums.ArticleCodes;
import com.ktdsuniversity.edu.commons.exceptions.enums.ExceptionType;
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

	private static final Logger logger = LoggerFactory.getLogger(RepliesServiceImpl.class);
	
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

	@Transactional
	@Override
	public RepliesVO createNewReplies(String articleId, RegistRepliesVO registRepliesVO) {
		
		ArticlesVO article = this.articlesDao.selectArticleByArticleId(articleId);
		
		if (article == null) {
//			throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
			throw new ArticleException(ExceptionType.ARTICLES, ArticleCodes.NOT_EXISTS);
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
		
//		throw new IllegalArgumentException("입력값이 유효하지 않습니다.");
		throw new ArticleException(ExceptionType.ARTICLES, null);
	}

	@Transactional
	@Override
	public String deleteReplies(String articleId, String repliesId) {
		RepliesVO replies= this.repliesDao.selectRepliesByRepliesId(articleId,repliesId);
		
		
		int deleteCount = this.repliesDao.deleteOneReplies(articleId, repliesId);
		if(deleteCount == 0) {
//			throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
			throw new ArticleException(ExceptionType.REPLIES, ArticleCodes.NOT_EXISTS);
		}
		int deletefile = this.multipartHandler.deleteFiles(replies.getFileSetId());
		logger.info("{}개의 파일이 삭제되었습니다.", deletefile);
		return replies.getId();
	}

	@Transactional
	@Override
	public long recommendReplies(String articleId, String repliesId) {
		
		int result = this.repliesDao.recommendReplies(articleId, repliesId);
		if(result == 0) {
//			throw new IllegalArgumentException("존재하지 않는 댓글 입니다.");
			throw new ArticleException(ExceptionType.REPLIES, ArticleCodes.NOT_EXISTS);
		}
		
		RepliesVO replies = this.repliesDao.selectRepliesByRepliesId(articleId, repliesId);
		return replies.getRecommendCnt();
	}

	@Transactional
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
		logger.debug("{}개의 로우가 업데이트되었습니다.",updateRows);
		if(updateRows == 0) {
//			throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
			throw new ArticleException(ExceptionType.REPLIES, ArticleCodes.NOT_EXISTS);
		}
		
		return this.repliesDao.selectRepliesByRepliesId(articleId, repliesId);
	}

}
