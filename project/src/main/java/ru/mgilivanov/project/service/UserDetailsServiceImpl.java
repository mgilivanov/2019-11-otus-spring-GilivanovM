package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mgilivanov.project.domain.User;
import ru.mgilivanov.project.repository.UserRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("user with login " + userName + " not found!"));

        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(userName);
        builder.password(user.getPassword());
        builder.roles(user.getRoles().stream().map(i -> i.getRole()).collect(Collectors.toList()).toArray((new String[0])));
       // builder.disabled(!user.isEnabled());
        return builder.build();
    }
}
