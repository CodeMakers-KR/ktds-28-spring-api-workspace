package com.ktdsuniversity.edu.members.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ktdsuniversity.edu.commons.exceptions.ArticleException;
import com.ktdsuniversity.edu.commons.exceptions.enums.ArticleCodes;
import com.ktdsuniversity.edu.commons.exceptions.enums.ExceptionType;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

@SpringBootTest // Spring이 생성하고 관리하는 Bean을 자동 주입 받기 위한 애노테이션
public class MembersServiceImplTest {

	// SpringBootTest가 준비한 bean을 주입 받는다.
	@Autowired
	private MembersService membersService;

	// DB에 직접 접근하지 않을것이기 때문에, 가짜 Bean을 생성.
	// MockitoBean이 MembersServiceImpl로 주입된다.
	@MockitoBean
	private MembersDao membersDao;

	@Test
	@DisplayName("회원가입 실패 테스트 - 이메일 중복")
	public void testCreateNewMemberDuplicateEmail() {
		// given
		BDDMockito.given(this.membersDao.selectEmailCount("test@gmail.com")).willReturn(1); // 이미 존재하는 이메일

		RegistMembersVO registMembersVO = new RegistMembersVO();
		registMembersVO.setEmail("test@gmail.com");
		registMembersVO.setName("TestUser");
		registMembersVO.setNickname("TestNickname");
		registMembersVO.setPassword("test_password");
		
		// when
		// ArticleException(type=MEMBERS, code=USED) 예외 발생 ==> 정상
		// MembersVO membersVO = this.membersService.createNewMember(registMembersVO);

		// 예외가 발생하는 것이 성공 케이스.
		// 예외가 발생하지 않으면 실패한다.
		ArticleException ae = assertThrows(ArticleException.class,
				() -> this.membersService.createNewMember(registMembersVO));
		
		// then
		assertEquals(ae.getType(), ExceptionType.MEMBERS);
		assertEquals(ae.getCodes(), ArticleCodes.USED);
	}

	@Test
	@DisplayName("회원가입 실패 테스트 - 닉네임 중복")
	public void testCreateNewMemberDuplicateNickname() {

	}

	@Test
	@DisplayName("회원가입 실패 테스트 - Insert 실패")
	public void testCreateNewMemberFailureInsert() {

	}

	@Test
	@DisplayName("회원가입 실패 테스트 - 비밀번호 암호화 실패(password == null)")
	public void testCreateNewMemberFailureEncryption() {

	}

	@Test
	@DisplayName("회원가입 성공 테스트")
	public void testCreateNewMember() {
		RegistMembersVO registMembersVO = new RegistMembersVO();
		registMembersVO.setEmail("test@gmail.com");
		registMembersVO.setName("TestUser");
		registMembersVO.setNickname("TestNickname");
		registMembersVO.setPassword("test_password");

		// Test pattern => Given -> When -> Then
		// Given - membersDao에게 역할 부여
		// membersDao.selectEmailCount에게 "test@gmail.com"이 전달되면, 0을 반환하도록 역할 부여
		BDDMockito.given(this.membersDao.selectEmailCount("test@gmail.com"))
				  .willReturn(0);

		BDDMockito.given(this.membersDao.selectNicknameCount("TestNickname"))
				  .willReturn(0);

		BDDMockito.given(this.membersDao.insertNewMember(registMembersVO))
				  .willReturn(1);

		MembersVO returnedMember = new MembersVO();

		BDDMockito.given(this.membersDao.selectMemberByEmail("test@gmail.com"))
				  .willReturn(returnedMember);

		// When
		MembersVO membersVO = this.membersService.createNewMember(registMembersVO);
		System.out.println("membersVO => " + membersVO);
		System.out.println("registMembersVO => " + registMembersVO);

		// Then
		// 반환값이 올바른지 검증(Given에서 주었던 값과 일치하는지)
		assertNotNull(membersVO);
		assertEquals(membersVO, returnedMember);

		// 비밀번호가 올바르게 암호화되었는지 확인.
		assertNotNull(registMembersVO.getSalt());
		assertNotEquals("test_password", registMembersVO.getPassword());

	}
}
