package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getCurrentUser() {
//        TODO: return current user from session?
        return createUser(0, "nikita_pobrito", "0", "Никита", "#");
    }

    private User createUser(final long id, final String nickName, final String password, final String name,
                            final String avatarUrl) {
        final User user = new User();

        user.setId(id);
        user.setNickName(nickName);
        user.setPassword(password);
        user.setName(name);
        user.setAvatarUrl(avatarUrl);

        return user;
    }
}
