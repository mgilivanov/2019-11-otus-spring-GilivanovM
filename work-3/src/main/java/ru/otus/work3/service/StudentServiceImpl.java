package ru.otus.work3.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.work3.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {
    private final UserInterfaceService userInterfaceService;
    private final LocalizationService localizationService;

    public StudentServiceImpl(UserInterfaceService userInterfaceService, MessageSource messageSource, LocalizationService localizationService){
        this.userInterfaceService = userInterfaceService;
        this.localizationService = localizationService;
    }
    public Student getStudent(){
        String lastName;
        String firstName;
        lastName = this.userInterfaceService.getInput(localizationService.getStudentLastName());
        firstName = this.userInterfaceService.getInput(localizationService.getStudentFirstName());
        return new Student(lastName, firstName);
    }
}
