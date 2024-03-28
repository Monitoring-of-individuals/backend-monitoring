package ru.monitoring.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.monitoring.model.PendingVerificationUser;
import ru.monitoring.repository.PendingVerificationUserRepository;
import ru.monitoring.service.user.IPendingVerificationUserService;

@Service
@RequiredArgsConstructor
public class PendingVerificationUserService implements IPendingVerificationUserService {
    private final PendingVerificationUserRepository pendingVerificationUserRepository;

    @Override
    public void save(PendingVerificationUser pendingVerificationUser) {
        pendingVerificationUserRepository.save(pendingVerificationUser);
    }
}
