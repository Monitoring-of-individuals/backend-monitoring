package ru.monitoring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.monitoring.model.PendingVerificationUser;

@Repository
public interface PendingVerificationUserRepository extends
        CrudRepository<PendingVerificationUser, String> {
}
