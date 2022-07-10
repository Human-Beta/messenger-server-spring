package com.nikita.messenger.server.service;

import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.Message;

import java.util.Optional;

public interface MessageService {
    Optional<Message> getLastMessageFrom(Chat chat);
}
