package com.nikita.messenger.server.controller;

import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.data.MessageRequestData;
import com.nikita.messenger.server.dto.MessageDTO;
import com.nikita.messenger.server.dto.MessageRequestDTO;
import com.nikita.messenger.server.dto.PaginationDTO;
import com.nikita.messenger.server.facade.MessageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController extends AbstractController {
    @Autowired
    private MessageFacade messageFacade;

    @GetMapping
    public List<MessageDTO> getMessagesFromChat(@RequestParam final long chatId, @Valid final PaginationDTO pagination) {
        final List<MessageData> messages = messageFacade.getMessagesFromChat(chatId, pagination.getPage(), pagination.getSize());

        return mapAll(messages, MessageDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    TODO: validation for message?
    public MessageDTO sendMessage(@RequestBody final MessageRequestDTO messageRequest) {
//        TODO: remove. It is a draft fix for 'Data truncation: Out of range value for column 'local_id' at row 1' error
        messageRequest.setLocalId(0);
        final MessageRequestData messageRequestData = map(messageRequest, MessageRequestData.class);

        final MessageData savedMessage = messageFacade.saveMessageToChat(messageRequestData);

        return map(savedMessage, MessageDTO.class);
    }

}
