package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.facade.ChatFacade;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.impl.MyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatFacadeImpl implements ChatFacade {

    @Autowired
    private ChatService chatService;

    @Autowired
    private MyConversionService conversionService;

    @Override
    public List<ChatData> getChatsFor(final UserData user) {
        final List<Chat> chats = chatService.getChatsFor(user);

        return conversionService.convertAll(chats, ChatData.class);
    }
}
