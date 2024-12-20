package com.oxygensened.userprofile.infrastructure.jpa;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

// TODO This class is a workaround to register the UserPreUpdateEventListener as a Hibernate event listener

@Configuration
public class BeanPostProcessorImpl implements BeanPostProcessor {

    private final UserEventListener userChangesInterceptor;

    BeanPostProcessorImpl(UserEventListener userChangesInterceptor) {
        this.userChangesInterceptor = userChangesInterceptor;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof EntityManagerFactory entityManagerFactory) {
            SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
            EventListenerRegistry serviceRegistry = sessionFactory
                    .getServiceRegistry()
                    .getService(EventListenerRegistry.class);

            serviceRegistry.getEventListenerGroup(EventType.POST_UPDATE)
                           .appendListener(userChangesInterceptor);

            serviceRegistry.getEventListenerGroup(EventType.POST_INSERT)
                           .appendListener(userChangesInterceptor);
        }
        return bean;
    }
}
