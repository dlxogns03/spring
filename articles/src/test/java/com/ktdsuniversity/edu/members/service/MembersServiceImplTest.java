package com.ktdsuniversity.edu.members.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

@SpringBootTest // Spring이 생성하고 관리하는 Bean을 자동 주입 받기 위한 에노테이션
//@ExtendWith(SpringExtension.class)// JUNIT5 사용 명시
//@Import({MembersDao.class, MembersServiceImpl.class})// junit6에서는 사용하지 않음 MembersServiceImpl <-- @Import(주입이 필요한 Bean(여러개가 필요할 경우 {}로 감싼다))
public class MembersServiceImplTest {
	
	// SpringBootTest와 Import가 준비한 bean을 주입 받는다.
	@Autowired
	private MembersService membersService;

//	@Autowired => ServiceImpl 자체 코드가 잘 돌아가는지가 궁금하기 때문에 DB는 상관이 없다. 즉 @Autowired는 필요하지 않다.
	@MockitoBean //Mockito: 가짜 @MockitoBean =>껍데기만 있는 빈을 가져와라 
	private MembersDao membersDao;

	@Test
	@DisplayName("회원가입 성공 테스트")
	public void testCreateNewMember() {
		RegistMembersVO registMembersVO = new RegistMembersVO();
		registMembersVO.setEmail("test@gmail.com");
		registMembersVO.setName("testUser");
		registMembersVO.setNickname("TestNicname"); 
		registMembersVO.setPassword("test_password");
		// Test pattern => Given(역할 부여) -> when(실행) -> then(예상한 결과)
		// Given - membersDao에게 역할 부여
		// BDDMockito.given(null); -> membersDao.selectEmailCount에게 "test@gmail.com"이 전달되면, 0을 반환하도록 역할 부여
		
		//Given
		BDDMockito.given(this.membersDao.selectMemberByEmail("test@gmail.com"))
				  .willReturn(0);
		
		BDDMockito.given(this.membersDao.selectNicnameByNickname("TestNicname"))
		  .willReturn(0);
		
		BDDMockito.given(this.membersDao.insertNewMember(registMembersVO))
		  .willReturn(1);
		
		MembersVO returnedMember = new MembersVO();
		
		BDDMockito.given(this.membersDao.selectMember("test@gmail.com"))
		  .willReturn(returnedMember);
		
		
		//When
		MembersVO membersVO = this.membersService.createNewMember(registMembersVO);
		System.out.println("membersVO => " + membersVO);
		System.out.println("registMembersVO => " + registMembersVO);
		
		// Then
		// 반환값이 올바른지 검증 (Given에서 주었던 값과 일치하는지)
		assertNotNull(membersVO);
		assertEquals(membersVO, returnedMember); // <= 메모리가 동일해야한다.
		// 비밀번호가 올바르게 암호화되었는지 확인.
		assertNotNull(registMembersVO.getSalt());
		assertNotEquals("test_password", registMembersVO.getPassword());
		
	}
}
