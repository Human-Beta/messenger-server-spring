package com.nikita.messenger.server.facade;

import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.data.UserRegistrationData;

import java.util.List;

public interface UserFacade {
    UserData getCurrentUser();

    UserData createUser(UserRegistrationData userRegistration);

    List<UserData> getUnknownUsersByNickname(String nickname, int page, int size);
}
