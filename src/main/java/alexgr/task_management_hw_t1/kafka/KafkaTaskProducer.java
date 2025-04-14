package alexgr.task_management_hw_t1.kafka;

import alexgr.task_management_hw_t1.dto.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTaskProducer {
    private final KafkaTemplate<String, Task> template;

    public void send(String topic, Task task) {
        try {
            template.send(topic, task);
        } catch (Exception e) {
            log.error("произошла ошибка с отправкой сообщения {} в топик {}", task.toString(), topic, e);


        }
    }
}
