package com.deivix.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    /**
     * Define the Kafka topic used in the demo.
     * - 2 partitions allow parallel consumption.
     * - 1 replica is enough for local development.
     */
    @Bean
    public NewTopic[] transactionNumber() {
        return new NewTopic[]{

                TopicBuilder.name("transaction-started")
                        .partitions(1)
                        .replicas(1)
                        .build(),

                TopicBuilder.name("transaction-submitted")
                        .partitions(1)
                        .replicas(1)
                        .build(),

                TopicBuilder.name("transaction-finished")
                        .partitions(1)
                        .replicas(1)
                        .build(),

                TopicBuilder.name("transaction-termianted")
                        .partitions(2)
                        .replicas(1)
                        .build(),
        };
    }

    @Bean
    public NewTopic[] transactionLetter() {
        return new NewTopic[]{

                TopicBuilder.name("transaction-AAA")
                        .partitions(2)
                        .replicas(1)
                        .build(),

                TopicBuilder.name("transaction-BBB")
                        .partitions(1)
                        .replicas(1)
                        .build(),

                TopicBuilder.name("transaction-CCC")
                        .partitions(1)
                        .replicas(1)
                        .build(),
        };
    }
}
