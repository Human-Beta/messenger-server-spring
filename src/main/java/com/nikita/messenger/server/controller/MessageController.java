package com.nikita.messenger.server.controller;

import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.dto.MessageDTO;
import com.nikita.messenger.server.dto.PaginationDTO;
import com.nikita.messenger.server.facade.MessageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController extends AbstractController {

    @Autowired
    private MessageFacade messageFacade;


    @GetMapping
    public List<MessageDTO> getMessagesForChat(@RequestParam final long chatId, @Valid final PaginationDTO pagination) {
        final List<MessageData> messages = messageFacade.getMessagesFromChat(chatId);

        return convertAllToDto(messages, MessageDTO.class);
    }

}
