package com.nikita.messenger.server.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.dto.ChatDTO;
import com.nikita.messenger.server.dto.MessageDTO;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.populator.PrivateChatPopulator;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Service
public class SocketIOServiceImpl implements SocketIOService {
    private static final Logger LOG = LoggerFactory.getLogger(SocketIOServiceImpl.class);

    private static final String NEW_MESSAGE_EVENT = "message";
    private static final String NEW_CHAT_EVENT = "chat";
    private static final String TOKEN_URL_PARAM = "token";
    private static final String USER_KEY = "user";

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
    @Autowired
    private MyConversionService conversionService;
    @Autowired
    private PrivateChatPopulator privateChatPopulator;

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
        client.set(USER_KEY, user);

        chatService.getAllChatsFor(user).stream().map(this::getRoom).forEach(client::joinRoom);

        clients.put(user.getId(), client.getSessionId());

        LOG.debug("Clients {}", clients);
    }

    private String getRoom(final Chat chat) {
        return Long.toString(chat.getId());
    }

    private void onDisconnect(final SocketIOClient client) {
        final User user = getCurrentUser(client);

        clients.remove(user.getId());

        LOG.debug("Client disconnected {}", client.getSessionId());
        LOG.debug("Clients {}", clients);
    }

    @Override
    public void sendMessage(final Message message) {
        final MessageDTO messageDTO = modelMapper.map(message, MessageDTO.class);
        final String room = Long.toString(messageDTO.getChatId());

        getCurrentClient().ifPresentOrElse(
                client -> messengerNamespace.getRoomOperations(room).sendEvent(NEW_MESSAGE_EVENT, client, messageDTO),
                () -> messengerNamespace.getRoomOperations(room).sendEvent(NEW_MESSAGE_EVENT, messageDTO));
    }

    private Optional<SocketIOClient> getCurrentClient() {
        final User user = userService.getCurrentUser();

        return getClientForUser(user);
    }

    private Optional<SocketIOClient> getClientForUser(final User user) {
        return of(user).map(User::getId).map(clients::get).map(uuid -> {
            LOG.debug("Found socket client UUID {}", uuid);

            return of(messengerNamespace.getClient(uuid));
        }).orElseGet(() -> {
            LOG.info("Socket connection was not established for user {}", user.getNickname());

            return empty();
        });
    }

    @Override
    public void sendChat(final Chat chat) {
        final Optional<SocketIOClient> currentClient = getCurrentClient();

        final String room = getRoom(chat);

        getClients(chat).forEach(chatClient -> {
            chatClient.joinRoom(room);

            if (currentClient.map(isSameWith(chatClient)).orElse(FALSE)) {
                return;
            }

            final User user = chatClient.get(USER_KEY);
            final ChatDTO chatDTO = getChatDTO(chat, user);
            chatClient.sendEvent(NEW_CHAT_EVENT, chatDTO);
        });
    }

    private List<SocketIOClient> getClients(final Chat chat) {
        return chat.getUsers()
                .stream()
                .map(this::getClientForUser)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static Function<SocketIOClient, Boolean> isSameWith(final SocketIOClient client) {
        return currClient -> currClient.equals(client);
    }

    private ChatDTO getChatDTO(final Chat chat, final User user) {
        final ChatData chatData = conversionService.convert(chat, ChatData.class);

        if (chatService.isPrivate(chat)) {
            final User partner = getPartner(chat, user);
            privateChatPopulator.populate(chatData, partner);
        }

        return modelMapper.map(chatData, ChatDTO.class);
    }

    private User getPartner(final Chat chat, final User user) {
        return chatService.getPartner(chat, user);
    }

    private User getCurrentUser(final SocketIOClient client) {
        authenticate(client);

        return userService.getCurrentUser();
    }

    private void authenticate(final SocketIOClient client) {
        final String token = client.getHandshakeData().getUrlParams().get(TOKEN_URL_PARAM).get(0);

        final OAuth2Authentication authentication = tokenServices.loadAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void stop() {
        LOG.debug("Socket IO server is stopped!");
        socketIOServer.stop();
    }
}
