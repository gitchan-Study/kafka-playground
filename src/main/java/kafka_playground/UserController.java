package kafka_playground;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private static final String TOPIC_USER_REGISTERED = "user-registered";
    private final Map<String, User> users = new HashMap<>();
    private final BCryptPasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public UserController(KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (users.containsKey(user.getId())) {
            return "Username already exists!";
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User newUser = new User(user.getId(), encodedPassword);
        users.put(newUser.getId(), newUser);

        // Publish user-registered event to Kafka
        UserRegisteredEvent event = new UserRegisteredEvent(newUser.getId());
        kafkaTemplate.send(TOPIC_USER_REGISTERED, event);

        System.out.println("Published user-registered event for: " + newUser.getId());

        return "User registered successfully!";
    }
}
