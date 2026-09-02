package com.vestige.repository;

import com.vestige.model.RecyclingRequest;
import com.vestige.model.User;
import com.vestige.model.VendorProfile;
import com.vestige.model.enums.RecyclingStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RecyclingRequestRepository extends JpaRepository<RecyclingRequest, UUID> {
    List<RecyclingRequest> findByUserIdOrderByCreatedAtDesc(UUID userId);
    List<RecyclingRequest> findByVendorIdOrderByCreatedAtDesc(UUID vendorId);
    List<RecyclingRequest> findByStatusOrderByCreatedAtDesc(RecyclingStatusType status);
    List<RecyclingRequest> findAllByOrderByCreatedAtDesc();

    long countByCreatedAtBetween(OffsetDateTime from, OffsetDateTime to);

    long countByStatusAndCreatedAtBetween(RecyclingStatusType status, OffsetDateTime from, OffsetDateTime to);

    long countByVendorAndCreatedAtBetween(VendorProfile vendor, OffsetDateTime from, OffsetDateTime to);

    long countByVendorAndStatusAndCreatedAtBetween(VendorProfile vendor, RecyclingStatusType status, OffsetDateTime from, OffsetDateTime to);

    long countByUserAndCreatedAtBetween(User user, OffsetDateTime from, OffsetDateTime to);

    long countByUserAndStatusAndCreatedAtBetween(User user, RecyclingStatusType status, OffsetDateTime from, OffsetDateTime to);

    @Query("SELECT COALESCE(SUM(r.deviceCount), 0) FROM RecyclingRequest r WHERE r.status = 'COMPLETED' AND r.createdAt BETWEEN :from AND :to")
    Long sumCompletedDeviceCount(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);

    @Query("SELECT COALESCE(SUM(r.deviceCount), 0) FROM RecyclingRequest r WHERE r.user = :user AND r.status = 'COMPLETED' AND r.createdAt BETWEEN :from AND :to")
    Long sumCompletedDeviceCountForUser(@Param("user") User user, @Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);

    @Query("SELECT r.status, COUNT(r) FROM RecyclingRequest r WHERE r.createdAt BETWEEN :from AND :to GROUP BY r.status")
    List<Object[]> countGroupedByStatus(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);

    @Query("SELECT r.status, COUNT(r) FROM RecyclingRequest r WHERE r.vendor = :vendor AND r.createdAt BETWEEN :from AND :to GROUP BY r.status")
    List<Object[]> countGroupedByStatusForVendor(@Param("vendor") VendorProfile vendor, @Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);
}
