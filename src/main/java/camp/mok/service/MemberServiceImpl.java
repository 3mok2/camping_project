package camp.mok.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import camp.mok.domain.ArticleVO;
import camp.mok.domain.AuthVO;
import camp.mok.domain.MemberVO;
import camp.mok.exception.PasswordMisMatchException;
import camp.mok.repository.AuthRepository;
import camp.mok.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	@Override
	public void join(MemberVO vo) {
		vo.setMemberPwd(passwordEncoder.encode(vo.getMemberPwd()));
		AuthVO authVO = new AuthVO(vo.getMemberId(),vo.getMemberNickName(),"ROLE_MEMBER");
		memberRepository.insert(vo);
		authRepository.insert(authVO);
	}
	
	@Override
	public MemberVO read(String memberId) {
		return memberRepository.selectById(memberId);
	}

	@Override
	public void modify(MemberVO vo) {
		memberRepository.update(vo);
	}

	@Transactional
	@Override
	public void changePassword(Map<String, String> memberMap) {
		String memberId = memberMap.get("memberId");
		String newPwd = memberMap.get("newPwd");
		String currentPwd = memberMap.get("currentPwd");
		MemberVO vo = memberRepository.selectById(memberId);
		if(!passwordEncoder.matches(currentPwd, vo.getMemberPwd())) {
			throw new PasswordMisMatchException();
		}
		memberRepository.updatePassword(memberId, passwordEncoder.encode(newPwd));
	}

	@Override
	public List<ArticleVO> getListById(String memberId) {
		return memberRepository.getListById(memberId);
	}
	
}
