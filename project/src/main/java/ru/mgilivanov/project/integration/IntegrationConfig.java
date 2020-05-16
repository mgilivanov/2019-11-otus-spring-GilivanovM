package ru.mgilivanov.project.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import ru.mgilivanov.project.repository.EodRepository;
import ru.mgilivanov.project.service.EodToEmailTransformer;


@Configuration
@IntegrationComponentScan
public class IntegrationConfig {
    private static final int DEFAULT_QUEUE_CAPACITY = 100;
    private static final int DEFAULT_POLLER_PERIOD = 1000;

    private static final String TRANSFORM_START_METHOD_NAME = "transformStart";
    private static final String TRANSFORM_END_METHOD_NAME = "transformEnd";
    private static final String IS_CLOSED_MESSAGE = "isClosed";
    private static final String SAVE_METHOD_NAME = "save";

    @Autowired
    private EodRepository eodRepository;

    @Autowired
    private EodToEmailTransformer messageTransformer;

    @Autowired
    private JavaMailSender mailSender;

    @Bean
    public PollableChannel closeDayInChanel() {
        return MessageChannels.queue("closeDayInChanel", DEFAULT_QUEUE_CAPACITY).get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(DEFAULT_POLLER_PERIOD).get();
    }

    @Bean
    public IntegrationFlow closeEodFlow() {
        return f -> f.channel(closeDayInChanel())
                .handle(eodRepository, SAVE_METHOD_NAME)
                .route(Message.class, m ->  m.getHeaders().get(IS_CLOSED_MESSAGE, Boolean.class)
                        , mapping -> mapping.subFlowMapping(false, sub -> sub
                                .transform(messageTransformer, TRANSFORM_START_METHOD_NAME)
                                .handle(m -> {
                                        mailSender.send((SimpleMailMessage) m.getPayload());
                                })
                        )
                                .subFlowMapping(true, sub -> sub
                                        .transform(messageTransformer, TRANSFORM_END_METHOD_NAME)
                                        .handle(m -> {
                                            mailSender.send((SimpleMailMessage) m.getPayload());
                                        })
                                )
                );
    }
}
