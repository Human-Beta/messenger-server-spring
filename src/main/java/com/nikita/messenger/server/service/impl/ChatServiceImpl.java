package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.dao.ChatDAO;
import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDAO chatDAO;

    @Override
    public List<Chat> getChatsFor(final UserData user) {
        return chatDAO.getChatsFor(user.getId());
    }
}
