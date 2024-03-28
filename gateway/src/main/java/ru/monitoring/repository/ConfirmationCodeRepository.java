package ru.monitoring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.monitoring.model.ConfirmationCode;

@Repository
public interface ConfirmationCodeRepository extends CrudRepository<ConfirmationCode, String> {
}
