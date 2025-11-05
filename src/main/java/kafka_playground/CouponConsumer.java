package kafka_playground;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CouponConsumer {

    @KafkaListener(topics = "user-registered", groupId = "coupon-group")
    public void issueCoupon(UserRegisteredEvent event) {
        System.out.println("[Coupon] Coupon issued for user: " + event.getUserId());
        // TODO: Add actual coupon issuing logic here
    }
}
