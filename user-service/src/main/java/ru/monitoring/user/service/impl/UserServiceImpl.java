package ru.monitoring.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.monitoring.user.exception.UserAlreadyExistsException;
import ru.monitoring.user.exception.UserNotFoundException;
import ru.monitoring.user.model.User;
import ru.monitoring.user.repository.UserRepository;
import ru.monitoring.user.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public User save(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("A user with this email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }

    public User getByUserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

}
