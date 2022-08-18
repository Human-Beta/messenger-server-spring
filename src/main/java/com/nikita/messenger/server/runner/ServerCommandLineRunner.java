package com.nikita.messenger.server.runner;

import com.nikita.messenger.server.service.SocketIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ServerCommandLineRunner implements CommandLineRunner {

	@Autowired
	private SocketIOService socketIOService;

	@Override
	public void run(final String... args) {
//		socketIOService.start();
	}
}
