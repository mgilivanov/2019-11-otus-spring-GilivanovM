package ru.otus.work3.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class UserInterfaceServiceImpl implements UserInterfaceService {
    private final Scanner in;
    private final PrintStream out;

    public UserInterfaceServiceImpl(){
        this.in = new Scanner(System.in);
        this.out = System.out;

    }
    public void sendMessage(String message){
        this.out.println(message);
    }
    public String getInput(String comment){
        this.out.println(comment);
        return this.in.next();
    }


}
