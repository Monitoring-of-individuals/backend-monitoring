package ru.monitoring.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.monitoring.user.exception.ResourceAlreadyExistsException;
import ru.monitoring.user.exception.ResourceNotFoundException;
import ru.monitoring.user.model.User;
import ru.monitoring.user.repository.UserRepository;
import ru.monitoring.user.service.IUserService;

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
