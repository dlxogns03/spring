package com.ktdsuniversity.edu.members.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

@Mapper
public interface MembersDao {

	int selectMemberByEmail(String email);

	int selectNicnameByNickname(String nickname);

	int insertNewMember(RegistMembersVO registMembersVO);

	MembersVO selectMember(String email);

	int updateLoginStatus(String email);

	int updateLoginFailed(String email);

	int updateBlock(String email);

	int updateResetBlock(String email);

	int updateLogoutStatus(String email);

	int deleteMember(String email);

}
