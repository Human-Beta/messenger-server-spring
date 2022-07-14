package com.nikita.messenger.server.converter;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

public abstract class AbstractConverter<S, T> implements ApplicationContextAware, Converter<S, T> {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected ConversionService getConversionService() {
        return applicationContext.getBean(ConversionService.class);
    }
}
