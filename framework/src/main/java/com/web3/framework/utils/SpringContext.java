package com.web3.framework.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @Author: smy
 * @Date: 2023/1/12 7:36 PM
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return (T) context.getBean(beanClass);
    }

    public static <T> T getBean(String name, Class<T> beanClass) {
        return context.getBean(name, beanClass);
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> Map<String,T> getBeans(Class<T> type) {
        return context.getBeansOfType(type);
    }

    public static Resource getResource(String location) {
        return context.getResource(location);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return context.getBeansWithAnnotation(annotationType);
    }

}
