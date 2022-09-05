package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.data.MessageRequestData;
import com.nikita.messenger.server.exception.ChatAccessException;
import com.nikita.messenger.server.exception.ChatNotFoundException;
import com.nikita.messenger.server.facade.MessageFacade;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.MessageService;
import com.nikita.messenger.server.service.SocketIOService;
import com.nikita.messenger.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MessageFacadeImpl extends AbstractFacade implements MessageFacade {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    @Autowired
    private SocketIOService socketIOService;

    @Override
//    TODO: place for transactional
    public List<MessageData> getMessagesFromChat(final long chatId, final Date sinceDate, final int size) {
        checkIfChatExists(chatId);
//        TODO: can I use spring security here? Maybe something like hasRole(tra_la_la)?
        checkChatAccess(chatId);

        final List<Message> messages = messageService.getMessagesFromChat(chatId, sinceDate, size);

        return convertAll(messages, MessageData.class);
    }

    private void checkIfChatExists(final long chatId) {
        if (!chatService.exists(chatId)) {
            throw new ChatNotFoundException(chatId);
        }
    }

    private void checkChatAccess(final long chatId) {
        final User user = userService.getCurrentUser();
        if (!chatService.isUserInChat(user, chatId)) {
            throw new ChatAccessException(user, chatId);
        }
    }

    @Override
//    TODO: place for transactional
    public MessageData saveMessageToChat(final MessageRequestData messageRequestData) {
        checkIfChatExists(messageRequestData.getChatId());
//        TODO: can I use spring security here? Maybe something like hasRole(tra_la_la)?
        checkChatAccess(messageRequestData.getChatId());

        final Message message = convert(messageRequestData, Message.class);

        final Message savedMessage = messageService.saveMessageToChat(message);

        socketIOService.sendMessage(savedMessage);

        return convert(savedMessage, MessageData.class);
    }
}
