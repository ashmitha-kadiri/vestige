package com.vestige.repository;

import com.vestige.model.RecyclingStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecyclingStatusHistoryRepository extends JpaRepository<RecyclingStatusHistory, UUID> {
    List<RecyclingStatusHistory> findByRequestIdOrderByCreatedAtAsc(UUID requestId);
}
