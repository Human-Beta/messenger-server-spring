package com.nikita.messenger.server.config;

import com.nikita.messenger.server.data.ChatData;
import com.nikita.messenger.server.data.MessageData;
import com.nikita.messenger.server.dto.ChatDTO;
import com.nikita.messenger.server.dto.MessageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.Boolean.TRUE;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;
import static org.modelmapper.convention.MatchingStrategies.STRICT;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();

//        TODO: check/refactor
        modelMapper.getConfiguration()
                .setMatchingStrategy(STRICT)
                .setFieldMatchingEnabled(TRUE)
                .setSkipNullEnabled(TRUE)
                .setFieldAccessLevel(PRIVATE)
                .setDeepCopyEnabled(TRUE);

        return modelMapper;
    }
}
