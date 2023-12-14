package com.intuit.craft.onlinecars.config;

import com.intuit.craft.onlinecars.constant.Constant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topic(){
        return TopicBuilder
                .name(Constant.KAFKA_TOPIC_CAR_ONBOARDED)
                .build();
    }

}
