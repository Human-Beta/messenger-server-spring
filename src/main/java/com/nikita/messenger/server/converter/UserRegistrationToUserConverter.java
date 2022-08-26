package com.nikita.messenger.server.converter;


import com.nikita.messenger.server.data.UserRegistrationData;
import com.nikita.messenger.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationToUserConverter implements Converter<UserRegistrationData, User> {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User convert(final UserRegistrationData registration) {
        final User user = new User();

        user.setNickname(registration.getNickname());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setName(registration.getName());

        return user;
    }
}
