package ru.monitoring.user.service;

import ru.monitoring.user.model.User;

public interface IUserService {

    User getByUserEmail(String email);

    User save(User user);

}
