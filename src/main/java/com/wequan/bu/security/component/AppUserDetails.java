package com.wequan.bu.security.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wequan.bu.repository.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

/**
 * @author ChrisChen
 */
public class AppUserDetails implements UserDetails {

    private Integer id;
//    private String name;
    private String nickname;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    private AppUserDetails(Integer id, String nickname, String email,
                          String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
//        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static AppUserDetails create(User user) {
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new AppUserDetails(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCredential(),
//                authorities
                null
        );
    }

    public Integer getId() {
        return id;
    }

//    public String getName() {
//        return name;
//    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickname;
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

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        if (this == obj){
            return true;
        }
        AppUserDetails that = (AppUserDetails) obj;
        return Objects.equals(id, that.getId());
    }
}
