package com.nikita.messenger.server.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.nikita.messenger.server.dto.MessageDTO;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.service.ChatService;
import com.nikita.messenger.server.service.SocketIOService;
import com.nikita.messenger.server.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SocketIOServiceImpl implements SocketIOService {
    private static final Logger LOG = LoggerFactory.getLogger(SocketIOServiceImpl.class);

    private static final String MESSAGE_EVENT = "message";
    private static final String TOKEN_URL_PARAM = "token";

    @Autowired
    private SocketIOServer socketIOServer;
    @Autowired
    private ResourceServerTokenServices tokenServices;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    private SocketIONamespace messengerNamespace;

    private final Map<Long, UUID> clients = new ConcurrentHashMap<>();

    @Override
    public void start() {
        messengerNamespace = socketIOServer.addNamespace("/messenger");

        messengerNamespace.addConnectListener(this::onConnect);
        messengerNamespace.addDisconnectListener(this::onDisconnect);

        socketIOServer.start();
    }

    private void onConnect(final SocketIOClient client) {
        LOG.debug("Client connected {}!!!", client.getSessionId());

        final User user = getCurrentUser(client);

        chatService.getAllChatsFor(user).stream()
                .map(Chat::getId)
                .map(chatId -> Long.toString(chatId))
                .forEach(client::joinRoom);

        clients.put(user.getId(), client.getSessionId());

        LOG.debug("Clients {}", clients);
    }

    private void authenticate(final SocketIOClient client) {
        final String token = client.getHandshakeData().getUrlParams().get(TOKEN_URL_PARAM).get(0);

        final OAuth2Authentication authentication = tokenServices.loadAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void onDisconnect(final SocketIOClient client) {
        final User user = getCurrentUser(client);

        clients.remove(user.getId());

        LOG.debug("Client disconnected {}!!!", client.getSessionId());
        LOG.debug("Clients {}", clients);
    }

    @Override
    public void sendMessage(final Message message) {
        final SocketIOClient client = getCurrentClient();

        final MessageDTO messageDTO = modelMapper.map(message, MessageDTO.class);

        messengerNamespace.getRoomOperations(Long.toString(messageDTO.getChatId()))
                .sendEvent(MESSAGE_EVENT, client, messageDTO);
    }

    private User getCurrentUser(final SocketIOClient client) {
//        TODO: use authorizationListener? (look at com/nikita/messenger/server/config/SocketConfig.java:22)
        authenticate(client);

        return userService.getCurrentUser();
    }

    private SocketIOClient getCurrentClient() {
        final User user = userService.getCurrentUser();

        return messengerNamespace.getClient(clients.get(user.getId()));
    }

    @Override
    public void stop() {
        LOG.debug("Socket IO server is stopped!");
        socketIOServer.stop();
    }
}
