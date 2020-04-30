package ru.otus.work17;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataMongoTest
@ContextConfiguration(initializers = {AbstractMongoIntegrationTest.Initializer.class})
@Testcontainers
public abstract class AbstractMongoIntegrationTest {

    @Container
    public static MongoDbContainer mongo = MongoDbContainer.getInstance();

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.data.mongodb.uri=mongodb://" + mongo.getContainerIpAddress() + ":"+mongo.getMappedPort(27017),
                    "spring.data.mongodb.database=library"
            ).applyTo(configurableApplicationContext);
        }
    }

}