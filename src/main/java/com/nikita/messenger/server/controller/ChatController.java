package com.nikita.messenger.server.controller;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.dto.ChatDTO;
import com.nikita.messenger.server.facade.ChatFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController extends AbstractController {
    @Autowired
    private ChatFacade chatFacade;

    @GetMapping
    public List<ChatDTO> getChatsForCurrentUser(
            @RequestParam(required = false, defaultValue = "-1") final List<Long> excludeIds,
            @RequestParam(defaultValue = "50") final int size) {
        final List<ChatData> chats = chatFacade.getChatsForCurrentUserExcludeIds(excludeIds, size);

        return mapAll(chats, ChatDTO.class);
    }

    @GetMapping("/search")
    public List<ChatDTO> getChatsWithNameStartsWith(
            @RequestParam final String name,
            @RequestParam(required = false, defaultValue = "1") final int page,
            @RequestParam(defaultValue = "50") final int size) {
        final List<ChatData> chats = chatFacade.getChatsForCurrentUserWithNameStarsWith(name, page, size);

        return mapAll(chats, ChatDTO.class);
    }
}
