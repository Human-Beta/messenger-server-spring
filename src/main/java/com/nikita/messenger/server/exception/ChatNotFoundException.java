package com.nikita.messenger.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(final long chatId) {
        super(format("There is no chat with id '%s'", chatId));
    }
}
