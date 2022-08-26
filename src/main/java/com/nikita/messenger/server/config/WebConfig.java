package com.nikita.messenger.server.config;

import com.nikita.messenger.server.converter.ChatConverter;
import com.nikita.messenger.server.converter.MessageConverter;
import com.nikita.messenger.server.converter.MessageDataRequestToMessageConverter;
import com.nikita.messenger.server.converter.MessageReverseConverter;
import com.nikita.messenger.server.converter.UserConverter;
import com.nikita.messenger.server.converter.UserRegistrationToUserConverter;
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
    private MessageDataRequestToMessageConverter messageDataRequestToMessageConverter;
    @Autowired
    private MessageReverseConverter messageReverseConverter;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private UserRegistrationToUserConverter userRegistrationToUserConverter;

    @Override
    public void addFormatters(final FormatterRegistry registry) {
//        TODO: scan packages for Converter implementation and register it instead of manually add converters
        registry.addConverter(chatConverter);
        registry.addConverter(messageConverter);
        registry.addConverter(messageDataRequestToMessageConverter);
        registry.addConverter(userConverter);
        registry.addConverter(messageReverseConverter);
        registry.addConverter(userRegistrationToUserConverter);
    }

}
