package com.nikita.messenger.server.exception;

import com.nikita.messenger.server.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ChatAccessException extends RuntimeException {
    public ChatAccessException(final User user, final long chatId) {
        super(format("User [nickname='%s'] is not allowed to access the chat [id='%s'], because he/she is not in it", user.getNickname(),
                     chatId));
    }
}
