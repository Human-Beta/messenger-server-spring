package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.repository.ChatRepository;
import com.nikita.messenger.server.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.PageRequest.ofSize;
import static org.springframework.data.domain.Pageable.unpaged;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public List<Chat> getChatsForUserExcludeIds(final User user, final List<Long> excludeIds, final int size) {
        return chatRepository.findAllByUsersIdAndIdNotIn(user.getId(), excludeIds,
                                                         ofSize(size).withSort(DESC, "lastMessage.date"));
    }

    @Override
    public List<Chat> getAllChatsFor(final User user) {
        return chatRepository.findAllByUsersId(user.getId(), unpaged());
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
