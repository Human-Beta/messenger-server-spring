package com.nikita.messenger.server.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class AbstractController {
    @Autowired
    private ModelMapper modelMapper;

    protected <T> List<T> convertAllToDto(final List<?> sources, final Class<T> clazz) {
        return sources.stream()
                .map(source -> convertToDto(source, clazz))
                .collect(Collectors.toList());
    }

    protected <T> T convertToDto(final Object source, final Class<T> clazz) {
        return modelMapper.map(source, clazz);
    }
}
