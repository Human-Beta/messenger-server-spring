package com.nikita.messenger.server.controller;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.dto.ChatDTO;
import com.nikita.messenger.server.dto.PaginationDTO;
import com.nikita.messenger.server.facade.ChatFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController extends AbstractController {
    @Autowired
    private ChatFacade chatFacade;

    @GetMapping
    public List<ChatDTO> getChats(@Valid final PaginationDTO pagination) {
        final List<ChatData> chats = chatFacade.getChatsForCurrentUser(pagination.getPage(), pagination.getSize());

        return mapAll(chats, ChatDTO.class);
    }
}
