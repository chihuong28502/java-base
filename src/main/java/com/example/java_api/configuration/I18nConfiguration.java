package com.example.java_api.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class I18nConfiguration implements WebMvcConfigurer {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3600); // Cache for 1 hour
        messageSource.setUseCodeAsDefaultMessage(true);
        System.out.println("messageSource: " + messageSource);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new HeaderLocaleResolver();
    }
}
