package net.dsa.todo.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.ToString;
import net.dsa.todo.model.User;

@ToString
@Getter
public class AuthenticatedUser implements UserDetails, Serializable{
	
	private static final long serialVersionUID = 1L; // Add this field
	private User user;
	
	public AuthenticatedUser(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// ? extends GrantedAuthority: GrantedAuthority type 을 extends 한 것이면 뭐든 상관없다.
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new SimpleGrantedAuthority(user.getRole().name()));
		return collect;
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getId();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 계정자체가 활성화 되었는지
	@Override
	public boolean isEnabled() {
		return UserDetails.super.isEnabled();
	}
}
