package alexgr.task_management_hw_t1.service;

import alexgr.task_management_hw_t1.dto.Task;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    public void sendStatusChangedNotification(Task task) throws MessagingException {
        String to = "chuvakchel@mail.ru";
        String subject = "Статус задачи обновлен";
        String text = String.format("Здравствуйте! Статус задачи '%s' изменён на: %s",
                task.title(), task.status());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bondjigaralex@yandex.ru");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        log.info("message from task {}, was posted from email {} ", task,to);

        mailSender.send(message);
    }
}
