package com.nikita.messenger.server.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ConfigWithSocket {

	@Value("${socket.server.host}")
	private String host;
	@Value("${socket.server.port}")
	private int port;

	@Bean
	public SocketIOServer socketIOServer() {
		final com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		config.setHostname(host);
		config.setPort(port);

		return new SocketIOServer(config);
	}
}
