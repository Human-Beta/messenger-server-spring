package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.builder;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String USER_ROLE = "USER";

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with nickname: " + username));

        return builder()
                .username(user.getNickname())
                .password(user.getPassword())
                .roles(USER_ROLE)
                .build();
    }
}
