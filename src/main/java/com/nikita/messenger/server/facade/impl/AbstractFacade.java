package com.nikita.messenger.server.facade.impl;

import com.nikita.messenger.server.service.impl.MyConversionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractFacade {
    @Autowired
    private MyConversionService conversionService;

    protected <T> List<T> convertAll(final List<?> objects, final Class<T> targetClass) {
        return conversionService.convertAll(objects, targetClass);
    }
}
