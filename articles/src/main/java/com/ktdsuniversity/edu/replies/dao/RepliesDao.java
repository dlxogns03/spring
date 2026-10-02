package com.ktdsuniversity.edu.replies.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktdsuniversity.edu.replies.vo.request.ModifyRepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.resqonse.RepliesVO;

@Mapper
public interface RepliesDao {

	RepliesVO selectRepliesByRepliesId(@Param("articleId") String articleId,@Param("repliesId") String repliesId);
	
	List<RepliesVO> readAllReplies(String articleId);

	int insertNewReplies(RegistRepliesVO registRepliesVO);

	int deleteOneReplies(@Param("articleId") String articleId,@Param("repliesId") String repliesId);

	int recommendReplies(@Param("articleId") String articleId,@Param("repliesId") String repliesId);

	int updateReplies(@Param("articleId") String articleId,
					  @Param("repliesId") String repliesId, 
					  @Param("modifyRepliesVO") ModifyRepliesVO modifyRepliesVO);

	

}
