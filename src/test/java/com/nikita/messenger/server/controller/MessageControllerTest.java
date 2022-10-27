package com.nikita.messenger.server.controller;

import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.data.MessageRequestData;
import com.nikita.messenger.server.dto.MessageDTO;
import com.nikita.messenger.server.dto.MessageRequestDTO;
import com.nikita.messenger.server.facade.MessageFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    private static final long CHAT_ID = 42;
    private static final Date SINCE_DATE = new Date();
    private static final int SIZE = 24;

    @Spy
    @InjectMocks
    private MessageController controller;

    @Mock
    private MessageFacade messageFacade;

    private final MessageDTO message = new MessageDTO();
    private final MessageData messageData = new MessageData();
    private final List<MessageData> messagesData = of(messageData);
    private final MessageRequestDTO messageRequestDTO = new MessageRequestDTO();
    private final MessageRequestData messageRequestData = new MessageRequestData();

    @Test
    void shouldReturnMessagesWhenGetMessagesFromChat() {
        when(messageFacade.getMessagesFromChat(CHAT_ID, SINCE_DATE, SIZE)).thenReturn(messagesData);
        doReturn(of(message)).when(controller).mapAll(messagesData, MessageDTO.class);

        final List<MessageDTO> messages = controller.getMessagesFromChat(CHAT_ID, SINCE_DATE, SIZE);

        assertThat(messages).containsExactly(message);
    }

    @Test
    void shouldReturnMessageWhenSendMessage() {
        doReturn(messageRequestData).when(controller).map(messageRequestDTO, MessageRequestData.class);
        doReturn(message).when(controller).map(messageData, MessageDTO.class);
        when(messageFacade.saveMessageToChat(messageRequestData)).thenReturn(messageData);

        final MessageDTO savedMessage = controller.sendMessage(messageRequestDTO);

        assertThat(savedMessage).isSameAs(message);
    }
}
