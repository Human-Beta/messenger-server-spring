package com.nikita.messenger.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Primary
public class MyConversionService implements ConversionService {

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    public <S, T> List<T> convertAll(final List<S> sources, final Class<T> targetClass) {
        return sources.stream()
                .map(source -> convert(source, targetClass))
                .collect(toList());
    }

    @Override
    public boolean canConvert(final Class<?> sourceType, final Class<?> targetType) {
        return conversionService.canConvert(sourceType, targetType);
    }

    @Override
    public boolean canConvert(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
        return conversionService.canConvert(sourceType, targetType);
    }

    @Override
    public <T> T convert(final Object source, final Class<T> targetType) {
        return conversionService.convert(source, targetType);
    }

    @Override
    public Object convert(final Object source, final TypeDescriptor sourceType, final TypeDescriptor targetType) {
        return conversionService.convert(source, sourceType, targetType);
    }
}
