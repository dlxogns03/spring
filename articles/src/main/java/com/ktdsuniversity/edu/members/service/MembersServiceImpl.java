package com.ktdsuniversity.edu.members.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.commons.crypto.AES;
import com.ktdsuniversity.edu.commons.crypto.encrypt.hash.SHA;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.request.LoginMemberVO;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MembersServiceImpl implements MembersService{

	@Value("${app.encrypt.aes.key}")
	private String aesSecretKey;
	
	private final MembersDao membersDao;
	
	
	@Override
	public MembersVO createNewMember(RegistMembersVO registMembersVO) {
		int email = this.membersDao.selectMemberByEmail(registMembersVO.getEmail());
		if(email > 0) {
			throw new IllegalArgumentException("중복된 이메일입니다");
		}
		
		
		int nickname = this.membersDao.selectNicnameByNickname(registMembersVO.getNickname());
		if(nickname > 0) {
			throw new IllegalArgumentException("중복된 닉네임입니다.");
		}
		
		String rawName = registMembersVO.getName();
		String encryptedName = AES.encode(this.aesSecretKey, rawName);
		registMembersVO.setName(encryptedName);
		
		String rawNickname = registMembersVO.getNickname();
		String encryptedNickname = AES.encode(this.aesSecretKey, rawNickname);
		registMembersVO.setNickname(encryptedNickname);
		
		String salt = SHA.generateSalt();
		String encryptPassword = SHA.getEncrypt(registMembersVO.getPassword(), salt);
		registMembersVO.setSalt(salt);
		registMembersVO.setPassword(encryptPassword);
		
		int insertMember = this.membersDao.insertNewMember(registMembersVO);
		
		if(insertMember > 0 ) {
			MembersVO member = this.membersDao.selectMember(registMembersVO.getEmail());
			return member;
		}
		
		throw new IllegalArgumentException("입력값이 유효하지 않습니다");
	}


	@Override
	public MembersVO readMember(LoginMemberVO loginMemberVO) {
		
		MembersVO membersVO = this.membersDao.selectMember(loginMemberVO.getEmail());
		
		if(membersVO == null) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
		}
		
		if (membersVO.getLoginBlockYn().equals("Y")) {
			//차단계정
			
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime loginBlockDate = LocalDateTime.parse(membersVO.getLoginBlockDate(),formatter);
			loginBlockDate = loginBlockDate.plusHours(1);
			
			if(now.isAfter(loginBlockDate) || now.equals(loginBlockDate) ) {
				//차단 후 1시간 경과 
				// 로그인 실패횟수 0으로 초기화 & 차단 여부 n으로 수정 
				int updateRows = this.membersDao.updateResetBlock(loginMemberVO.getEmail());
				System.out.println(updateRows + "건이 블락해제되었음.");
			}else {
				throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
			}
		}
			
		//활성계정
		String rawPassword = loginMemberVO.getPassword();
		String storedSalt = membersVO.getSalt();
		String encryptedPassword =  SHA.getEncrypt(rawPassword, storedSalt);
		
		if(encryptedPassword.equals(membersVO.getPassword() )){
			//비밀번호 일치함
			int updateRows = this.membersDao.updateLoginStatus(membersVO.getEmail());
			
			if(updateRows == 0) {
				throw new IllegalArgumentException("로그인을 실패했습니다. 잠시 후 다시 시도해주세요.");
			}
			
			
			MembersVO loggedMember =  this.membersDao.selectMember(membersVO.getEmail());
			loggedMember.setName(AES.decode(this.aesSecretKey, loggedMember.getName()));
			loggedMember.setNickname(AES.decode(this.aesSecretKey, loggedMember.getNickname()));
			
			return loggedMember;
		}
		
		//비밀번호 불일치 
		int updateRows = this.membersDao.updateLoginFailed(membersVO.getEmail());
		System.out.println(membersVO.getEmail()+"로그인 실패 !");
		
		int blockUpdateRows = this.membersDao.updateBlock(membersVO.getEmail());
		if(blockUpdateRows > 0) {
			//계정이 차단됨
			throw new IllegalArgumentException("로그인 실패 횟수가 누적되어 계정이 차단되었습니다. 잠시후 재시도 해주세요");
		}else {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
		}
		
	}


	@Override
	public String updateLogoutStatus(String email) {
		int updatedRows = this.membersDao.updateLogoutStatus(email);
		if(updatedRows > 0) {
			return email;
		}
		return null;
	}


	@Override
	public String deleteMember(String email, String password) {
		
		MembersVO loggedMember =  this.membersDao.selectMember(email);
		String rowPassword = SHA.getEncrypt(password, loggedMember.getSalt());
		
		if(!loggedMember.getPassword().equals(rowPassword)) {
			System.out.println(loggedMember.getSalt());
			System.out.println(loggedMember.getPassword());
			System.out.println(password);
			System.out.println(rowPassword);
			throw new IllegalArgumentException("비밀번호가 틀립니다.");
			
		}
		
		int updateRows = this.membersDao.deleteMember(email);
		if(updateRows == 0) {
			throw new IllegalArgumentException("탈퇴실패 했습니다");
		}
		this.membersDao.updateLogoutStatus(email);
		
		return email+ "님 탈퇴 되었습니다.";
	}

}
