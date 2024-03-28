package ru.monitoring.service.user;

import ru.monitoring.model.User;

public interface IUserService {

    User getByUserEmail(String email);

    User save(User user);
}
