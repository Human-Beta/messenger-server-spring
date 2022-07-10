package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.UserData;
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
}
