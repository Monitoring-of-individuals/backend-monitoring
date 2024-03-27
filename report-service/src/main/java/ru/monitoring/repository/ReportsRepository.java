package ru.monitoring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.monitoring.model.ReportEntity;

@Repository
public interface ReportsRepository extends JpaRepository<ReportEntity, Long> {

    Page<ReportEntity> findAllByUserIdOrderByReportDateTimeDesc(Long userId, Pageable page);
}
