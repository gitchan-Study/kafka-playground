package kafka_playground;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private static final String TOPIC_USER_REGISTERED = "user-registered";
    private final Map<String, User> users = new HashMap<>();
    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public UserController(KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (users.containsKey(user.getId())) {
            return "Username already exists!";
        }

        String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        User newUser = new User(user.getId(), encodedPassword);
        users.put(newUser.getId(), newUser);

        // Publish user-registered event to Kafka
        UserRegisteredEvent event = new UserRegisteredEvent(newUser.getId());
        kafkaTemplate.send(TOPIC_USER_REGISTERED, event);

        System.out.println("Published user-registered event for: " + newUser.getId());

        return "User registered successfully!";
    }
}
