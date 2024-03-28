package ru.monitoring.service.user;

import ru.monitoring.model.PendingVerificationUser;

public interface IPendingVerificationUserService {
    void save(PendingVerificationUser pendingVerificationUser);
}
