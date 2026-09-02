package com.vestige.repository;

import com.vestige.model.Redemption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RedemptionRepository extends JpaRepository<Redemption, UUID> {
    List<Redemption> findByUserIdOrderByCreatedAtDesc(UUID userId);

    long countByCreatedAtBetween(java.time.OffsetDateTime from, java.time.OffsetDateTime to);
}
