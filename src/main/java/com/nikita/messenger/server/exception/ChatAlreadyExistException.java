package com.nikita.messenger.server.exception;

import com.nikita.messenger.server.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.CONFLICT)
public class ChatAlreadyExistException extends RuntimeException {
    public ChatAlreadyExistException(final User user1, final User user2) {
        super(format("Private chat for users ['%s', '%s'] already exists", user1.getNickname(), user2.getNickname()));
    }
}
