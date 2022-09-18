package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.repository.UserRepository;
import com.nikita.messenger.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        final UserDetails userDetails = getCurrentUserDetails();

        return getUserByNickname(userDetails.getUsername());
    }

    protected UserDetails getCurrentUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public User getUserByNickname(final String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with nickname: " + nickname));
    }

    @Override
    public User save(final User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return userRepository.existsByNickname(nickname);
    }

}
