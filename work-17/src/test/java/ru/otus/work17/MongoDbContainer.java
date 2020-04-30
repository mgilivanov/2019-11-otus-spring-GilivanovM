package ru.otus.work17;

import org.testcontainers.containers.GenericContainer;

public class MongoDbContainer extends GenericContainer<MongoDbContainer> {

    public static final int MONGODB_PORT = 27017;
    public static final String DEFAULT_IMAGE_AND_TAG = "mongo:4.0";
    private static MongoDbContainer mongoDbContainer;

    private MongoDbContainer() {
        super(DEFAULT_IMAGE_AND_TAG);
    }

    public static MongoDbContainer getInstance() {
        if (mongoDbContainer == null) {
            mongoDbContainer = new MongoDbContainer().withExposedPorts(MONGODB_PORT);
        }
        return mongoDbContainer;
    }
}
