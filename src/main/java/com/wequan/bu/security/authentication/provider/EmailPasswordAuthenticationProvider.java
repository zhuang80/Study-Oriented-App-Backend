package com.wequan.bu.security.authentication.provider;

import com.wequan.bu.exception.EmailNotVerifiedException;
import com.wequan.bu.repository.dao.UserMapper;
import com.wequan.bu.repository.model.User;
import com.wequan.bu.security.authentication.token.EmailPasswordAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ChrisChen
 */
public class EmailPasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailPasswordAuthenticationToken token = null;
        if (authentication.getPrincipal() != null && authentication.getCredentials() != null) {
            String email = authentication.getName();
            String password = authentication.getCredentials().toString();
            //认证逻辑
            User user = userMapper.selectByEmail(email);
            if (user != null) {
                if (!user.getEmailVerified()) {
                    throw new EmailNotVerifiedException();
                }
                String credential = user.getCredential();
                if (!StringUtils.isBlank(credential) && passwordEncoder.matches(password, credential)) {
                    token = new EmailPasswordAuthenticationToken(user.getId(), authentication.getCredentials(), listGrantedAuthorities(email));
                    token.setDetails(authentication.getDetails());
                }
            }
        }
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailPasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Set<GrantedAuthority> listGrantedAuthorities(String email) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (!StringUtils.isBlank(email)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }
}
