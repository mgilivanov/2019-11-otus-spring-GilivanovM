package ru.otus.work7.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("locale")
public class LocaleSettings {
    private String language;
    private String country;

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
