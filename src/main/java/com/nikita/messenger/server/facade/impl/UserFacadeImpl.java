package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.data.UserRegistrationData;
import com.nikita.messenger.server.exception.UserAlreadyExistException;
import com.nikita.messenger.server.facade.UserFacade;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class UserFacadeImpl implements UserFacade {
    @Autowired
    private UserService userService;
    @Autowired
    private ConversionService conversionService;

    @Override
    public UserData getCurrentUser() {
        final User currentUser = userService.getCurrentUser();

        return conversionService.convert(currentUser, UserData.class);
    }

    @Override
    public UserData createUser(final UserRegistrationData userRegistration) {
//        TODO: do not need to check. Just to handle an error from DB that nickname exists
        if (userService.existsByNickname(userRegistration.getNickname())) {
            throw new UserAlreadyExistException(userRegistration.getNickname());
        }

        final User userToSave = conversionService.convert(userRegistration, User.class);
        final User createdUser = userService.save(userToSave);

        return conversionService.convert(createdUser, UserData.class);
    }
}
