package ru.monitoring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.monitoring.model.RevokedToken;

/**
 * Репозиторий для хранения отозванных токенов
 */
@Repository
public interface RevokedTokenRepository extends CrudRepository<RevokedToken, String> {
}
