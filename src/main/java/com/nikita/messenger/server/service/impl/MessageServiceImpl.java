package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.repository.MessageRepository;
import com.nikita.messenger.server.service.MessageService;
import com.nikita.messenger.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;

    @Override
    public Optional<Message> getLastMessageFrom(final Chat chat) {
        return messageRepository.findFirstByChatIdOrderByDateDesc(chat.getId());
    }

    @Override
    public List<Message> getMessagesFromChat(final long chatId, final int page, final int size) {
        return messageRepository.findAllByChatIdOrderByDateDesc(chatId, of(page - 1, size));
    }

    @Override
    public Message saveMessageToChat(final Message message) {
//        TODO: validate message
        message.setSenderId(userService.getCurrentUser().getId());
        message.setDate(new Date());

        return messageRepository.save(message);
    }
}
