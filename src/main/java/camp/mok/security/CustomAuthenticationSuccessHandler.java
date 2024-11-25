package camp.mok.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		// 로그인 하지 않은 경우 Spring Security가 요청을 가로챔
		// 사용자의 요청 정보 저장
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if(savedRequest!=null) {
			log.info(savedRequest.getRedirectUrl());
			response.sendRedirect(savedRequest.getRedirectUrl());
			return;
		}
		// 사용자가 로그인 페이지로 직접 이동하는 경우
		String prevPage = (String) request.getSession().getAttribute("prevPage");
		if(prevPage!=null) {
			request.getSession().removeAttribute("prevPage"); // 이전페이지 받아서
			response.sendRedirect(prevPage); // redirect
			return;
		}
		// 주소창에서 로그인 페이지로
		response.sendRedirect(request.getContextPath());
	}
	
	
}
