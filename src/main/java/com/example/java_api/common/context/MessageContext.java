package com.example.java_api.common.context;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageContext {

    private static final ThreadLocal<String> MESSAGE = new ThreadLocal<>();
    private static MessageSource messageSource;

    public MessageContext(MessageSource messageSource) {
        MessageContext.messageSource = messageSource;
    }

    public static void setMessage(String message) {
        MESSAGE.set(message);
    }

    public static String getMessage() {
        return MESSAGE.get();
    }

    public static void clear() {
        MESSAGE.remove();
    }

    /**
     * Get message by key with current locale
     */
    public static String getMessage(String key) {
        if (messageSource == null) {
            return key;
        }
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    /**
     * Get message by key with parameters
     */
    public static String getMessage(String key, Object... args) {
        if (messageSource == null) {
            return key;
        }
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    /**
     * Get message by key with specific locale
     */
    public static String getMessage(String key, Locale locale) {
        if (messageSource == null) {
            return key;
        }
        return messageSource.getMessage(key, null, locale);
    }

    /**
     * Get message by key with parameters and specific locale
     */
    public static String getMessage(String key, Object[] args, Locale locale) {
        if (messageSource == null) {
            return key;
        }
        return messageSource.getMessage(key, args, locale);
    }
}
