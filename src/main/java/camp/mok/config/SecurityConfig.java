package camp.mok.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import camp.mok.security.CustomUserDetailService;
import lombok.extern.log4j.Log4j;

@Configuration
@EnableWebSecurity
@ComponentScan("camp.mok.security")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	// Spring Security의 웹 보안 설정
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/guest/**").permitAll() // guest로 접근
//			.antMatchers("/member/**").access("hasRole('ROLE_MEMBER')") // member만 가능
//			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')"); // admin 가능
		
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("utf-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, CsrfFilter.class);
		
		http.formLogin().loginPage("/login")
						.loginProcessingUrl("/member/login")
						.usernameParameter("memberId")
						.passwordParameter("memberPwd")
						.successHandler(authenticationSuccessHandler)
						.failureHandler(authenticationFailureHandler);
		
		http.logout().logoutUrl("/logout").invalidateHttpSession(true)
					.deleteCookies("remember-me", "JSESSION_ID");
		
		http.rememberMe().key("mok")
			.tokenRepository(persistentTokenRepository())
			.tokenValiditySeconds(604800);
		
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService)
		.passwordEncoder(PasswordEncoder());
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}

	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
