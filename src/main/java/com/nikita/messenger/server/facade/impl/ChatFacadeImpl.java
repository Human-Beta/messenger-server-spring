package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.facade.ChatFacade;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatFacadeImpl extends AbstractFacade implements ChatFacade {
    @Autowired
    private ChatService chatService;

    @Override
    public List<ChatData> getChatsFor(final UserData user, final int page, final int size) {
        final List<Chat> chats = chatService.getChatsFor(user, page, size);

        return convertAll(chats, ChatData.class);
    }
}
