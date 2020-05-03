package ru.otus.work13.bee.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.otus.work13.domain.User;
import ru.otus.work13.repository.UserRepository;

@Component
public class UserConfig {
    public UserConfig(UserRepository userRepository, PasswordEncoder passwordEncoder){
        User user = new User("1", "user", passwordEncoder.encode("pass"), true, true, true, true);
        user.addRole("USER");
        userRepository.save(user);

        User admin = new User("2", "admin", passwordEncoder.encode("admin"), true, true, true, true);
        admin.addRole("USER");
        admin.addRole("ADMIN");
        userRepository.save(admin);
    }
}
