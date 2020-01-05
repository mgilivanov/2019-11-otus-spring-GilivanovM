package ru.otus.work3.domain;

public class Student {
    private final String lastName;
    private final String firstName;
    public Student(String lastName, String firstName){
        this.lastName = lastName;
        this.firstName = firstName;
    }
    public String getLastName(){
        return this.lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
