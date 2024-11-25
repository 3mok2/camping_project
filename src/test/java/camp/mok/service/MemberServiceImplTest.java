package camp.mok.service;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import camp.mok.AppTest;
import camp.mok.domain.AuthVO;
import camp.mok.domain.MemberVO;
import camp.mok.repository.AuthRepository;

public class MemberServiceImplTest extends AppTest{

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private AuthRepository authRepository;
	
	@Test
//	@Ignore
	public void test() {
		MemberVO vo = new MemberVO();
		vo.setMemberId("admin");
		vo.setMemberPwd("1234");
		vo.setMemberName("관리자");
		vo.setMemberPhone("01022222222");
		vo.setMemberNickName("모기");
		vo.setEmail("admin@test.com");
		memberService.join(vo);
		
		AuthVO authVO = new AuthVO("admin", "모기", "ROLE_ADMIN");
		authRepository.insert(authVO);
	}
	
	@Test
//	@Ignore
	public void test2() {
		MemberVO vo = new MemberVO(); 
		vo.setMemberId("scott");
		vo.setMemberPwd("1234");
		vo.setMemberName("스캇");
		vo.setMemberPhone("01011111111");
		vo.setMemberNickName("스캇");
		vo.setEmail("scott@test.com");
		memberService.join(vo);
	}

}
