package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.exception.ChatNotFoundException;
import com.nikita.messenger.server.facade.MessageFacade;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageFacadeImpl extends AbstractFacade implements MessageFacade {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatService chatService;

    @Override
//    TODO: place for transactional
    public List<MessageData> getMessagesFromChat(final long chatId, final int page, final int size) {
        checkIfChatExists(chatId);

        final List<Message> messages = messageService.getMessagesFromChat(chatId, page, size);

        return convertAll(messages, MessageData.class);
    }

    private void checkIfChatExists(final long chatId) {
        if (chatService.getChat(chatId).isEmpty()) {
            throw new ChatNotFoundException(chatId);
        }
    }

    @Override
//    TODO: place for transactional
    public MessageData putMessageToChat(final MessageData messageData) {
        checkIfChatExists(messageData.getChatId());

        final Message message = convert(messageData, Message.class);

        final long messageId = messageService.putMessageToChat(message);
        final Message savedMessage = messageService.getMessage(messageId)
                .orElseThrow(() -> new IllegalStateException("There is no message with id " + messageId));

        return convert(savedMessage, MessageData.class);
    }
}
