package com.wequan.bu.security.component;

import com.wequan.bu.repository.dao.UserMapper;
import com.wequan.bu.repository.model.User;
import com.wequan.bu.security.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author ChrisChen
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username as email
        // to do - check email format to avoid sql injection
        User user = userMapper.selectByEmail(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return AppUserDetails.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
        return AppUserDetails.create(user);
    }
}
