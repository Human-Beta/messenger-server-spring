package com.nikita.messenger.server.config;

import com.nikita.messenger.server.converter.ChatConverter;
import com.nikita.messenger.server.converter.MessageConverter;
import com.nikita.messenger.server.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private ChatConverter chatConverter;
    @Autowired
    private MessageConverter messageConverter;
    @Autowired
    private UserConverter userConverter;

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(chatConverter);
        registry.addConverter(messageConverter);
        registry.addConverter(userConverter);
    }

}
