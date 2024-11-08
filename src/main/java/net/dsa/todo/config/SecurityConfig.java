package net.dsa.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // 스프링 시큐리티 메소드 레벨 보안 활성화 
@Configuration
public class SecurityConfig {
	
	private final AuthenticationFailureHandler authenticationFailureHandler;
	@Bean
	@Order(1)
	public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
		// /admin/**  경로에 대한 모든 요청을 처리하는 필터
		.securityMatcher("/admin/**");
		http.authorizeHttpRequests(request -> request
			.requestMatchers("/admin/login")
			.permitAll()
			.anyRequest()
			.hasAnyRole("MANAGER", "ADMIN")
			)
		.httpBasic(Customizer.withDefaults())
		.formLogin(formLogin -> formLogin
		.loginPage("/admin/login")
		.usernameParameter("id")
		.loginProcessingUrl("/admin/login")
		.defaultSuccessUrl("/admin")
		.failureUrl("/admin/login")
		.permitAll())
		.logout(logout -> logout
		.logoutUrl("/admin/logout")
		.logoutSuccessUrl("/admin/login")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSION")
		);
		
		return http.build();	
	}
	
	@Bean
	@Order(2)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// csrf 설정을 비활성화 하다.
		http.csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(request -> request
			// 모든 사용자에게 접근을 허용하는 URL
			.requestMatchers("/", "/user/register", "/user/login")
			// 그 외 경로는 인증을 받은 사용자만 접근 가능
			.permitAll().anyRequest()
			.authenticated())
		// HTTP Basic 인증을 활성화
		.httpBasic(Customizer.withDefaults())
		// form login 방식 사용
		.formLogin(formLogin -> formLogin
		// 사용자가 만든 로그인 페이지 사용
		// 설정하지 않으면 기본 URL이 "/login"으로 호출된다.
		.loginPage("/user/login")
		// username parameter's default value = "username" but if you use different name set parameter name
		.usernameParameter("id")
		// password는 동일해서 안바꿔줘도 됨
//		.passwordParameter("")
		// Login 인증을 처리하는 URL
		.loginProcessingUrl("/user/login")
		// URL if Login success 
		.defaultSuccessUrl("/user/login-success")
		.failureUrl("/user/login-fail")
		// Login failure handler class
		.failureHandler(authenticationFailureHandler)
		.permitAll())
		.logout(logout -> logout
		.logoutUrl("/user/logout")
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSION")
		);
		
		return http.build();
	}
	

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
