package camp.mok.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import camp.mok.domain.MemberVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUser extends User{
	
	private static final long serialVersionUID = -1794558029817021167L;
	
	private String memberNickName;
	
	private MemberVO memberVO;
	
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public CustomUser(MemberVO memberVO) {
		super(memberVO.getMemberId(), memberVO.getMemberPwd(),
				memberVO.getAuthList().stream()
					.map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
					.collect(Collectors.toList()));
		this.memberVO = memberVO;
		this.memberNickName = memberVO.getMemberNickName();
	}
}

	
