package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.dao.MessageDAO;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.service.MessageService;
import com.nikita.messenger.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private UserService userService;

    @Override
    public Optional<Message> getMessage(final long messageId) {
        return messageDAO.getMessage(messageId);
    }

    @Override
    public Optional<Message> getLastMessageFrom(final Chat chat) {
        return messageDAO.getLastMessageFromChat(chat.getId());
    }

    @Override
    public List<Message> getMessagesFromChat(final long chatId, final int page, final int size) {
        return messageDAO.getMessagesFromChat(chatId, page, size);
    }

    @Override
    public long putMessageToChat(final Message message) {
//        TODO: validate message
        message.setSenderId(userService.getCurrentUser().getId());
        message.setDate(new Date());

        return messageDAO.putMessageToChat(message);
    }
}
