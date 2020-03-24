package ru.otus.work8.bee;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.work8.bee.changelog.DatabaseChangelog;

@Configuration
public class MongoBeeConfig {

    @Autowired
    private MongoClient mongo;
    private final String databaseName = "library";

    @Bean
    public Mongobee mongobee(Environment environment) {
        Mongobee runner = new Mongobee(mongo);
        runner.setDbName(databaseName);
        runner.setChangeLogsScanPackage(DatabaseChangelog.class.getPackage().getName());
        runner.setMongoTemplate(new MongoTemplate(mongo, databaseName));
        runner.setSpringEnvironment(environment);
        runner.setEnabled(true);
        return runner;
    }

}
