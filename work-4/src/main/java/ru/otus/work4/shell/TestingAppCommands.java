package ru.otus.work4.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.work4.service.TestingService;
import java.io.IOException;

@ShellComponent
public class TestingAppCommands {
    private final TestingService testingService;

    public TestingAppCommands(TestingService testingService) {
        this.testingService = testingService;
    }

    @ShellMethod(value = "run testing!", key = "run")
    public void startTesting() throws IOException {
        this.testingService.run();
    }

}
