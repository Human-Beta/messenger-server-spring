package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.dao.ChatDAO;
import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDAO chatDAO;

    @Override
    public List<Chat> getChatsFor(final UserData user, final int page, final int size) {
        return chatDAO.getChatsFor(user.getId(), page, size);
    }

    @Override
    public Optional<Chat> getChat(final long chatId) {
        return chatDAO.getChat(chatId);
    }
}
