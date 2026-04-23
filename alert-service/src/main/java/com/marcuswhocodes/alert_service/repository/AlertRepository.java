package com.marcuswhocodes.alert_service.repository;

import com.marcuswhocodes.alert_service.domain.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert,Long> {
}
