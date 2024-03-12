package ru.monitoring.user.repository;

import org.springframework.data.repository.CrudRepository;
import ru.monitoring.user.model.RevokedToken;

/**
 * Репозиторий для хранения отозванных токенов
 */
public interface RevokedTokenRepository extends CrudRepository<RevokedToken, String> {
}
