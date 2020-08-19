package com.senzzzp.kafkastreamsdemo.data;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Random;

@Configuration
public class InitialDataLoader implements ApplicationRunner {

    private KafkaTemplate<String, String> kafkaTemplate;

    public InitialDataLoader(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean
    public NewTopic demoTopic() {
        return TopicBuilder.name("demo-topic")
                .partitions(12)
                .replicas(3)
                .config("min.insync.replicas", "2")
                .build();
    }

    @Bean
    public NewTopic demoTopic2() {
        return TopicBuilder.name("demo-topic-2")
                .partitions(12)
                .replicas(3)
                .config("min.insync.replicas", "2")
                .build();
    }


    @Override
    public void run(ApplicationArguments args) {
        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            String key = String.valueOf(r.nextInt(1000000 - 1) + 1);
            String value = String.valueOf(r.nextInt(1000000 - 1) + 1);
            this.kafkaTemplate.send("demo-topic", key, value);
        }
    }

}
