package ru.otus.work16.bee.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.otus.work16.domain.User;
import ru.otus.work16.repository.UserRepository;

@Component
public class UserConfig {
    public UserConfig(UserRepository userRepository, PasswordEncoder passwordEncoder){
        User user = new User("1", "user", passwordEncoder.encode("pass"), true, true, true, true);
        userRepository.save(user);
    }
}
