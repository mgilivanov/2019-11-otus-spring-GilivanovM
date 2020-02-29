package ru.otus.work8.service;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import ru.otus.work8.config.LocaleSettings;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {
    private final Locale locale;
    private final ReloadableResourceBundleMessageSource messageSource;
    private final LocaleSettings localeSettings;

    public MessageServiceImpl(LocaleSettings localeSettings) {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        this.messageSource = ms;
        this.localeSettings = localeSettings;
        this.locale = new Locale(localeSettings.getLanguage(), localeSettings.getCountry());
    }

    public String getMessage(String messageCode, String[] args){
        return messageSource.getMessage(messageCode, args, locale);
    }
    public String getMessage(String messageCode){
        return messageSource.getMessage(messageCode, new String[]{}, locale);
    }
}
