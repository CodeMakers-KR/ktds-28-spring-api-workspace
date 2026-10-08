package com.ktdsuniversity.edu.members.web;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.ktdsuniversity.edu.members.service.MembersService;

// Controller 테스트를 위해 작성
// Test Request를 발생시켜 실제 End-Point를 호출하는 테스트
@WebMvcTest(MembersController.class)
public class MembersControllerTest {

	@Autowired
	private MockMvc mvc; // End-point에 Request를 대신 해주기 위한 객체
	
	private Gson gson;
	
	// MembersService given을 위해 작성
	@MockitoBean
	private MembersService membersService;
	
	/**
	 * MembersControllerTest가 시작될 때 한번만 실행됨.
	 */
	@BeforeAll
	public void setup() {
		this.gson = new Gson();
	}
}
