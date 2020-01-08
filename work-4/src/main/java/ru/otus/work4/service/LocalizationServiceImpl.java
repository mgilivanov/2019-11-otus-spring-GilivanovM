package ru.otus.work4.service;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import ru.otus.work4.config.LocaleSettings;
import ru.otus.work4.domain.Student;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final Locale locale;
    private final ReloadableResourceBundleMessageSource messageSource;
    private final LocaleSettings localeSettings;

    public LocalizationServiceImpl(LocaleSettings localeSettings) {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        this.messageSource = ms;
        this.localeSettings = localeSettings;
        this.locale = new Locale(localeSettings.getLanguage(), localeSettings.getCountry());
    }

    public String getStudentLastName() {
        return messageSource.getMessage("student.lastname", new String[]{}, locale);
    }

    public String getStudentFirstName() {
        return messageSource.getMessage("student.firstname", new String[]{}, locale);
    }

    public String getTestingResults(Student student, String result) {
        return messageSource.getMessage("testing.results", new String[]{student.getLastName(), student.getFirstName(), result}, locale);
    }

    public String getQuestionsFile() {
        return messageSource.getMessage("questions.file", new String[]{}, locale);
    }

}
