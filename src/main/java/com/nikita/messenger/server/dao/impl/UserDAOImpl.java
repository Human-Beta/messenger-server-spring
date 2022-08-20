package com.nikita.messenger.server.dao.impl;

import com.nikita.messenger.server.dao.UserDAO;
import com.nikita.messenger.server.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {
    @Override
    public Optional<User> getUserByNickname(final String nickname) {
        return Optional.of(createUser());
    }

    private User createUser() {
        final User user = new User();

        user.setId(0);
        user.setNickname("nikita");
        user.setPassword("112233");
        user.setName("Никита Шишов");
        user.setAvatarUrl("#");

        return user;
    }
}
