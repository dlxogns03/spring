package com.ktdsuniversity.edu.members.vo.response;

import java.util.List;

public class MembersListVO {
	
	private long membersCount;
	
	private List<MemberVO> membersListVO;

	public long getMembersCount() {
		return membersCount;
	}

	public void setMembersCount(long membersCount) {
		this.membersCount = membersCount;
	}

	public List<MemberVO> getMembersListVO() {
		return membersListVO;
	}

	public void setMembersListVO(List<MemberVO> membersListVO) {
		this.membersListVO = membersListVO;
	}

	@Override
	public String toString() {
		return "MembersListVO [membersCount=" + membersCount + ", membersListVO=" + membersListVO + "]";
	}
	
	
}
