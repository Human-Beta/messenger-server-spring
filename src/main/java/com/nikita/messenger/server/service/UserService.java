package com.nikita.messenger.server.service;

import com.nikita.messenger.server.model.User;

public interface UserService {
    User getCurrentUser();

    User getUserByNickname(String nickname);

}
