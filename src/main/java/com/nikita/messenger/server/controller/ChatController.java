package com.nikita.messenger.server.controller;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.dto.ChatDTO;
import com.nikita.messenger.server.facade.ChatFacade;
import com.nikita.messenger.server.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController extends AbstractController {

    @Autowired
    private ChatFacade chatFacade;
    @Autowired
    private UserFacade userFacade;

    @GetMapping
    public List<ChatDTO> getChats() {
//        TODO: need to return chats for a specific user by auth token
        final UserData user = userFacade.getCurrentUser();
        final List<ChatData> chats = chatFacade.getChatsFor(user);

        return convertAllToDto(chats, ChatDTO.class);
    }
}
