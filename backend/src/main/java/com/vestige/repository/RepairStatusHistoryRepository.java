package com.vestige.repository;

import com.vestige.model.RepairStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepairStatusHistoryRepository extends JpaRepository<RepairStatusHistory, UUID> {

    List<RepairStatusHistory> findByBookingIdOrderByCreatedAtAsc(UUID bookingId);
}
