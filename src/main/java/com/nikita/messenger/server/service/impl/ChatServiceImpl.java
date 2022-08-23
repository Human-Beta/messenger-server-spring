package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.repository.ChatRepository;
import com.nikita.messenger.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public List<Chat> getChatsFor(final UserData user, final int page, final int size) {
//        TODO: add sorting by last message date
//        return chatRepository.findAllByUserId(user.getId(), PageRequest.of(0, 3, Sort.by("name")));
        return chatRepository.findAllByUserId(user.getId(), of(page - 1, size));
    }

    @Override
    public boolean exists(final long chatId) {
        return chatRepository.existsById(chatId);
    }
}
