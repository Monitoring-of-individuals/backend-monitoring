package ru.monitoring.service;

import ru.monitoring.model.User;

public interface IUserService {

    User getByUserEmail(String email);

    User save(User user);
}
