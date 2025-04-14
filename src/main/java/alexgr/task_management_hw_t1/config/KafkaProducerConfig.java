package alexgr.task_management_hw_t1.config;

import alexgr.task_management_hw_t1.dto.Task;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.bootstrap.server}")
    private String servers;

    @Bean("producerTaskKafkaTemplate")
    public KafkaTemplate<String, Task> kafkaTemplate(ProducerFactory<String, Task> producerTaskFactory) {
        return new KafkaTemplate<>(producerTaskFactory);
    }

    @Bean
    public ProducerFactory<String, Task> producerTaskFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
