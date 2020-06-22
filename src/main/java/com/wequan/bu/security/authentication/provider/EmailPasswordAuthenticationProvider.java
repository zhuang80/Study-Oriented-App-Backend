package com.wequan.bu.security.authentication.provider;

import com.wequan.bu.security.authentication.token.EmailPasswordAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ChrisChen
 */
public class EmailPasswordAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        //认证逻辑
        System.out.println("EmailPasswordAuthenticationProvider -> authenticate");
        EmailPasswordAuthenticationToken token = new EmailPasswordAuthenticationToken(email, authentication.getCredentials(), listGrantedAuthorities(email));
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(EmailPasswordAuthenticationToken.class);
    }

    private Set<GrantedAuthority> listGrantedAuthorities(String userName) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (StringUtils.isBlank(userName)) {
            return authorities;
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
}
