package net.dsa.todo.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	// 사용자 인증 실패 시 실행되는 핸들러 메소드
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		log.info("onAuthenticationFailure call");
		log.info("Exception: {}", exception);
		/*
		 * 인증 실패의 예외 종류
		 * BadCredentialException: 아이디 또는 패스워드가 다르다.
		 * LockException: UserDetails 객체의 isAccountNonLocked() 메소드의 리턴값이 false일 경우
		 * DisabledException: UserDetails 객체의 isEnabled() 메소드의 리턴값이 false일 경우
		 * AccountExpiredException: UserDetails 객체의 isAccountNoneExpired() 메소드의 리턴 값이 false일 경우
		 * CredentialExpiredException: UserDetails 객체의 isCredentialNonExpired() 메소드의 리턴 값이 false일 경우
		 * */
		super.onAuthenticationFailure(request, response, exception);
	}
}
