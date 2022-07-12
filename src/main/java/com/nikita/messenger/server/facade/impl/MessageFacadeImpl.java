package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.exception.ChatNotFoundException;
import com.nikita.messenger.server.facade.MessageFacade;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MessageFacadeImpl extends AbstractFacade implements MessageFacade {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatService chatService;

    @Override
    public List<MessageData> getMessagesFromChat(final long chatId) {
        final Optional<Chat> chat = chatService.getChat(chatId);

        if (chat.isEmpty()) {
            throw new ChatNotFoundException(chatId);
        }

        final List<Message> messages = messageService.getMessagesFromChat(chatId);

        return convertAll(messages, MessageData.class);
    }
}
