package camp.mok.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import camp.mok.domain.MemberVO;
import camp.mok.repository.MemberRepository;

@Component
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVO vo = memberRepository.read(username);
		if(vo==null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new CustomUser(vo);
	}
}
