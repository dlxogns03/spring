package com.ktdsuniversity.edu.members.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.MemberVO;
import com.ktdsuniversity.edu.members.vo.MembersListVO;

@Service
public class MemverServiceImpl implements MemberService{
	
	private MembersDao membersdao;
	
	public MemverServiceImpl(MembersDao membersDao) {
		this.membersdao = membersDao;
	}
	
	
	@Override
	public MembersListVO readAllMembers() {
		long count = this.membersdao.selectMemberCount();
		List<MemberVO> membersList = this.membersdao.selectAllMembers();
		
		MembersListVO list = new MembersListVO();
		list.setMembersCount(count);
		list.setMembersListVO(membersList);
		
		return list;
	}

}
