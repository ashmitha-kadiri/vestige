package com.vestige.repository;

import com.vestige.model.AdminAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdminActionRepository extends JpaRepository<AdminAction, UUID> {
    List<AdminAction> findByAdminIdOrderByCreatedAtDesc(UUID adminId);
    List<AdminAction> findByTargetEntityOrderByCreatedAtDesc(String targetEntity);
    List<AdminAction> findAllByOrderByCreatedAtDesc();
}
