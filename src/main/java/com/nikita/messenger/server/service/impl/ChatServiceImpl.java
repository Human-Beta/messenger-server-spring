package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.repository.ChatRepository;
import com.nikita.messenger.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.by;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public List<Chat> getChatsFor(final User user, final int page, final int size) {
        return chatRepository.findAllByUserId(user.getId(), of(page - 1, size, by(DESC, "lastMessage.date")));
    }

    @Override
    public List<Chat> getAllChatsFor(final User user) {
        return chatRepository.findAllByUserId(user.getId());
    }

    @Override
    public boolean exists(final long chatId) {
        return chatRepository.existsById(chatId);
    }

    @Override
    public boolean isUserInChat(final User user, final long chatId) {
        return chatRepository.isUserInChat(user.getId(), chatId);
    }
}
