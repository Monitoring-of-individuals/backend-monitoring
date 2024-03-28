package ru.monitoring.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.monitoring.exception.ResourceAlreadyExistsException;
import ru.monitoring.exception.ResourceNotFoundException;
import ru.monitoring.model.User;
import ru.monitoring.repository.UserRepository;
import ru.monitoring.service.user.IUserService;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    public User getByUserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User with email %s not found", email)));
    }

    public User save(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    String.format("A user with this email %s already exists", user.getEmail()));
        }
        return userRepository.save(user);
    }
}
