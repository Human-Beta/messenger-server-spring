package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.exception.UserNotFoundException;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.repository.UserRepository;
import com.nikita.messenger.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

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
                .orElseThrow(() -> new UserNotFoundException("There is no user with nickname: " + nickname));
    }

    @Override
    public User save(final User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public List<User> getUsersByNicknameExcludeIds(final String nickname, final List<Long> excludedIds, final int page,
                                                   final int size) {
        return userRepository.findAllByNicknameStartingWithAndIdNotIn(nickname, excludedIds,
                                                                      of(page - 1, size).withSort(by("id")));
    }
}
