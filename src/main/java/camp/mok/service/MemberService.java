package camp.mok.service;

import java.util.List;
import java.util.Map;

import camp.mok.domain.ArticleVO;
import camp.mok.domain.MemberVO;

public interface MemberService {

	void join(MemberVO vo);
	
	void modify(MemberVO vo);
	
	MemberVO read(String memberId);
	
	void changePassword(Map<String, String> memberMap);
	
	List<ArticleVO> getListById(String memberId);
}
