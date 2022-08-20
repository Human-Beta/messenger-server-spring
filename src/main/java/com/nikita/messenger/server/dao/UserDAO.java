package com.nikita.messenger.server.dao;

import com.nikita.messenger.server.model.User;

import java.util.Optional;

public interface UserDAO {

    Optional<User> getUserByNickname(String nickname);
}
