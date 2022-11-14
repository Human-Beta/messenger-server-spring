package com.nikita.messenger.server.service;

import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.Message;

public interface SocketIOService {

	void start();

	void stop();

	void sendMessage(Message message);

	void sendChat(Chat chat);
}
