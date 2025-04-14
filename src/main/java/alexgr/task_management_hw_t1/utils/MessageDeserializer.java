package alexgr.task_management_hw_t1.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class MessageDeserializer<T> extends JsonDeserializer<T> {
    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        try {
            return super.deserialize(topic, headers, data);
        } catch (Exception e) {
            log.error("произошла ошибка во время десереализации сообщения {} ", new String(data, StandardCharsets.UTF_8));
            return null;
        }
    }

    @Override
    public T deserialize(String topic, byte[] data) {
         try {
             return super.deserialize(topic, data);
         } catch (Exception e) {
             log.error("произошла ошибка во время десереализации сообщения {} ", new String(data, StandardCharsets.UTF_8));
             return null;
         }
    }
}
