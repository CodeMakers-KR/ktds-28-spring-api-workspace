package com.ktdsuniversity.edu.members.dao;

import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;


@MybatisTest // MyBatis 테스트를 위해 작성.
             // *Dao.java와 *DaoMapper.xml 파일을 읽어 빈으로 생성한다.
// 실제 데이터베이스에 연결하기 위해 작성
// 누락하면 Test DB를 연결하게 되며, Test DB준비가 안되어 있을 경우 에러 발생한다.
@AutoConfigureTestDatabase(replace = Replace.NONE)
// DaoTest는 Insert, Update, Delete 테스트 실행 후 Rollback 한다.
// 시퀀스는 Rollback되지 않음.
public class MembersDaoTest {

	@Autowired
	private MembersDao membersDao;
	
}
