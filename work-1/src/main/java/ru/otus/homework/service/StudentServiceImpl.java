package ru.otus.homework.service;

import ru.otus.homework.domain.Student;

public class StudentServiceImpl implements StudentService {
    private final UserInterfaceService userInterfaceService;

    public StudentServiceImpl(UserInterfaceService userInterfaceService){
        this.userInterfaceService = userInterfaceService;
    }
    public Student getStudent(){
        String lastName;
        String firstName;
        lastName = this.userInterfaceService.getInput("Укажите свою  фамилию");
        firstName = this.userInterfaceService.getInput("Укажите своё имя");
        return new Student(lastName, firstName);
    }
}
