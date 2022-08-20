package com.nikita.messenger.server.converter;

import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, UserData> {

    @Override
    public UserData convert(final User user) {
        final UserData userData = new UserData();

        userData.setId(user.getId());
        userData.setNickname(user.getNickname());
        userData.setName(user.getName());
        userData.setAvatarUrl(user.getAvatarUrl());

        return userData;
    }

}
