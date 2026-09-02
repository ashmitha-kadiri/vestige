package com.vestige.repository;

import com.vestige.model.Payment;
import com.vestige.model.enums.PaymentStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByUserIdOrderByCreatedAtDesc(UUID userId);

    Optional<Payment> findByProviderOrderId(String providerOrderId);

    List<Payment> findByRelatedEntityTypeAndRelatedEntityIdOrderByCreatedAtDesc(String relatedEntityType, UUID relatedEntityId);

    List<Payment> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.createdAt >= :startDate AND p.createdAt <= :endDate")
    long countByDateRange(@Param("startDate") OffsetDateTime startDate, @Param("endDate") OffsetDateTime endDate);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = :status AND p.createdAt >= :startDate AND p.createdAt <= :endDate")
    long countByStatusAndDateRange(@Param("status") PaymentStatusType status,
                                   @Param("startDate") OffsetDateTime startDate,
                                   @Param("endDate") OffsetDateTime endDate);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.status = 'SUCCESS' AND p.createdAt >= :startDate AND p.createdAt <= :endDate")
    BigDecimal sumSuccessfulRevenueByDateRange(@Param("startDate") OffsetDateTime startDate,
                                               @Param("endDate") OffsetDateTime endDate);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = :status")
    long countByStatus(@Param("status") PaymentStatusType status);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.status = 'SUCCESS'")
    BigDecimal sumTotalSuccessfulRevenue();
}
