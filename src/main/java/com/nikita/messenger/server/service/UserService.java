package com.nikita.messenger.server.service;

import com.nikita.messenger.server.model.User;

import java.util.List;

public interface UserService {
    User getCurrentUser();

    User getUserByNickname(String nickname);

    List<User> getUsersByNicknameExcludeIds(String nickname, List<Long> excludedIds, int page, int size);

    User save(User user);

    boolean existsByNickname(String nickname);
}
