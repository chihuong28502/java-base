package com.example.java_api.configuration;

import java.util.Locale;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom LocaleResolver để đọc ngôn ngữ từ header x-language
 */
public class HeaderLocaleResolver implements LocaleResolver {

    private static final String LANGUAGE_HEADER = "x-language";
    private static final Locale DEFAULT_LOCALE = Locale.forLanguageTag("vi-VN");

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String languageHeader = request.getHeader(LANGUAGE_HEADER);
        
        if (StringUtils.hasText(languageHeader)) {
            try {
                // Hỗ trợ cả "vi" và "vi-VN", "en" và "en-US"
                if ("vi".equalsIgnoreCase(languageHeader)) {
                    return Locale.forLanguageTag("vi-VN");
                } else if ("en".equalsIgnoreCase(languageHeader)) {
                    return Locale.forLanguageTag("en-US");
                } else {
                    // Thử parse trực tiếp nếu có format khác
                    return Locale.forLanguageTag(languageHeader);
                }
            } catch (Exception e) {
                // Nếu có lỗi, fallback về default locale
                return DEFAULT_LOCALE;
            }
        }
        
        // Nếu không có header, fallback về default locale
        return DEFAULT_LOCALE;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        // Không cần implement vì chúng ta chỉ đọc từ header
        // Locale được set thông qua header x-language
    }
}
