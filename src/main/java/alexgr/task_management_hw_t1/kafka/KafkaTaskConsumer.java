package alexgr.task_management_hw_t1.kafka;

import alexgr.task_management_hw_t1.dto.Task;
import alexgr.task_management_hw_t1.service.NotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTaskConsumer {

    private final NotificationService notificationService;

    @KafkaListener(id = "${kafka.consumer.group-id}",
            topics = "${kafka.topic.task_status_exchanged}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload Task task, Acknowledgment acknowledgment) throws MessagingException {
        try {
            log.info("Получено сообщение о задаче: {}", task);
            notificationService.sendStatusChangedNotification(task);
        } finally {
            acknowledgment.acknowledge();
        }
    }
}
