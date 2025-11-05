package kafka_playground;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @KafkaListener(topics = "user-registered", groupId = "email-group")
    public void sendWelcomeEmail(UserRegisteredEvent event) {
        System.out.println("[Email] Welcome email sent to user: " + event.getUserId());
        // TODO: Add actual email sending logic here
    }
}
