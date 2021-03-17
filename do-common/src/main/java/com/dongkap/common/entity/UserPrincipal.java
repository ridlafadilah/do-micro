package com.dongkap.common.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3037050208842046200L;
	private String id;
	private String username;
    private String password;
	private boolean enabled = true;
	private boolean accountNonExpired = true;
	private boolean credentialsNonExpired = true;
	private boolean accountNonLocked = true;
    private String name;
    private String email;
	private String provider = "local";
	private String verificationCode;
	private Date verificationExpired;
	private String raw;
	private String authorityDefault;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes = new HashMap<String, Object>();

}
