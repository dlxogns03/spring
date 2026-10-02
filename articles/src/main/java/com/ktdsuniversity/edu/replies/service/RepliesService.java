package com.ktdsuniversity.edu.replies.service;

import com.ktdsuniversity.edu.replies.vo.request.ModifyRepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.resqonse.RepliesListVO;
import com.ktdsuniversity.edu.replies.vo.resqonse.RepliesVO;

public interface RepliesService {

	RepliesListVO readAllReplies(String articleId);

	RepliesVO createNewReplies(String articleId, RegistRepliesVO registRepliesVO);

	String deleteReplies(String articleId,String repliesId);

	long recommendReplies(String articleId, String repliesId);

	RepliesVO updateReplies(String articleId, String repliesId, ModifyRepliesVO modifyRepliesVO);

}
