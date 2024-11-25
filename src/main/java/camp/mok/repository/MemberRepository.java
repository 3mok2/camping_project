package camp.mok.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import camp.mok.domain.ArticleVO;
import camp.mok.domain.MemberVO;

public interface MemberRepository {
	
	MemberVO read(String memberId);
	
	void insert(MemberVO vo);
	
	void update(MemberVO vo);
	
	MemberVO selectById(String memberId);
	
	void updatePassword(
			@Param("memberId") String memberId,
			@Param("memberPwd") String memberPwd);
	
	String selectByEmail(String email);
	
	List<ArticleVO> getListById(String memberId);
}
