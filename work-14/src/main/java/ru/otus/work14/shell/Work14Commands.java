package ru.otus.work14.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import static ru.otus.work14.config.JobConfig.*;

@RequiredArgsConstructor
@ShellComponent
public class Work14Commands {
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    @SneakyThrows
    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() {
        Long executionId = jobOperator.startNextInstance(IMPORT_LIBRARY_JOB_NAME);
        System.out.println(jobOperator.getSummary(executionId));
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_LIBRARY_JOB_NAME));
    }
}
