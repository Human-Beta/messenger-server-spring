package com.nikita.messenger.server.facade;

import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.data.UserRegistrationData;

public interface UserFacade {
    UserData getCurrentUser();

    UserData createUser(UserRegistrationData userRegistration);
}
