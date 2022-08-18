package com.nikita.messenger.server.service.impl;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.nikita.messenger.server.dto.MessageDTO;
import com.nikita.messenger.server.service.SocketIOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.stream.Collectors.joining;

@Service
public class SocketIOServiceImpl implements SocketIOService {

    private static final Logger LOG = LoggerFactory.getLogger(SocketIOServiceImpl.class);

    private static final String MESSAGE_EVENT = "message";

    @Autowired
    private final SocketIOServer socketIOServer;

    private SocketIONamespace chatNamespace;

    public SocketIOServiceImpl(final SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @Override
    public void start() {
        chatNamespace = socketIOServer.addNamespace("/chat");

        chatNamespace.addConnectListener(this::onConnect);
        chatNamespace.addDisconnectListener(this::onDisconnect);
        chatNamespace.addEventListener("chat", MessageDTO.class, this::onMessage);


//        socketIOServer.addConnectListener(this::onConnect);
//        socketIOServer.addDisconnectListener(this::onDisconnect);
////        socketIOServer.addEventListener("chat", MessageDTO.class, this::onMessage);
//        socketIOServer.addEventListener("chat", Object.class, this::test);

        socketIOServer.start();

//        socketIOServer.getAllNamespaces().stream().map(SocketIONamespace::getName).forEach(System.out::println);

//        final Configuration config = socketIOServer.getConfiguration();
//        LOG.debug("Socket IO server started on host '{}' and port '{}'!", config.getHostname(), config.getPort());
    }

    private void test(final SocketIOClient socketIOClient, final Object o, final AckRequest ackRequest) {
        System.out.println(o);
    }

    private void onConnect(final SocketIOClient client) {
        LOG.debug("Client connected!!!");

//        client.joinRoom("room1");

//        socketIOServer.getBroadcastOperations().sendEvent("test", "Hello");
//        logClientInfo(client);
    }

    private void logClientInfo(final SocketIOClient client) {
        LOG.debug("All clients in the namespace: {}", getAllClientsFrom(client.getNamespace()));
        LOG.debug("Namespace: '{}'", client.getNamespace().getName());
        LOG.debug("Session id: {}", client.getSessionId());
        LOG.debug("Handshake data: {}", client.getHandshakeData());
        LOG.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private String getAllClientsFrom(final SocketIONamespace namespace) {
        return namespace.getAllClients().stream()
                .map(SocketIOClient::getSessionId)
                .map(UUID::toString)
                .collect(joining(", "));
    }

    private void onDisconnect(final SocketIOClient client) {
        LOG.debug("Client disconnected!!!");
//        logClientInfo(client);
    }

    @SuppressWarnings("unused")
    private void onMessage(final SocketIOClient client, final MessageDTO message, final AckRequest ackRequest) {
        LOG.debug("Client: {}", client.getSessionId());
        LOG.debug("Received message: {}", message);
//        chatNamespace.getBroadcastOperations().sendEvent(MESSAGE_EVENT, message);
//        chatNamespace.getBroadcastOperations().sendEvent("test", message);
        socketIOServer.getBroadcastOperations().sendEvent("chat", message);
    }

    @Override
    public void stop() {
        LOG.debug("Socket IO server stopped!");
        socketIOServer.stop();
    }
}
