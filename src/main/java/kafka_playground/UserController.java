package kafka_playground;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final Map<String, User> users = new HashMap<>();
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (users.containsKey(user.getId())) {
            return "Username already exists!";
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User newUser = new User(user.getId(), encodedPassword);
        users.put(newUser.getId(), newUser);

        System.out.println("Registered User: " + newUser.getId());
        System.out.println("Encoded Password: " + newUser.getPassword());

        return "User registered successfully!";
    }
}
