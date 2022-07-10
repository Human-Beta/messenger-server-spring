package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.dao.MessageDAO;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDAO messageDAO;

    @Override
    public Optional<Message> getLastMessageFrom(final Chat chat) {
        return messageDAO.getLastMessageFromChat(chat.getId());
    }
}
