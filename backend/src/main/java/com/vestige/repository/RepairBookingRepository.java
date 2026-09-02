package com.vestige.repository;

import com.vestige.model.RepairBooking;
import com.vestige.model.User;
import com.vestige.model.VendorProfile;
import com.vestige.model.enums.BookingStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RepairBookingRepository extends JpaRepository<RepairBooking, UUID> {

    List<RepairBooking> findByUserIdOrderByCreatedAtDesc(UUID userId);

    List<RepairBooking> findByVendorIdOrderByCreatedAtDesc(UUID vendorId);

    List<RepairBooking> findByStatusOrderByCreatedAtDesc(BookingStatusType status);

    List<RepairBooking> findAllByOrderByCreatedAtDesc();

    List<RepairBooking> findBySubmissionId(UUID submissionId);

    @Query("SELECT r FROM RepairBooking r WHERE r.submission.id = :submissionId AND r.status NOT IN ('CANCELLED', 'REJECTED')")
    Optional<RepairBooking> findActiveBookingBySubmissionId(@Param("submissionId") UUID submissionId);

    long countByCreatedAtBetween(OffsetDateTime from, OffsetDateTime to);

    long countByStatusAndCreatedAtBetween(BookingStatusType status, OffsetDateTime from, OffsetDateTime to);

    long countByVendorAndCreatedAtBetween(VendorProfile vendor, OffsetDateTime from, OffsetDateTime to);

    long countByVendorAndStatusAndCreatedAtBetween(VendorProfile vendor, BookingStatusType status, OffsetDateTime from, OffsetDateTime to);

    long countByUserAndCreatedAtBetween(User user, OffsetDateTime from, OffsetDateTime to);

    long countByUserAndStatusAndCreatedAtBetween(User user, BookingStatusType status, OffsetDateTime from, OffsetDateTime to);

    @Query("SELECT r.status, COUNT(r) FROM RepairBooking r WHERE r.createdAt BETWEEN :from AND :to GROUP BY r.status")
    List<Object[]> countGroupedByStatus(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);

    @Query("SELECT r.status, COUNT(r) FROM RepairBooking r WHERE r.vendor = :vendor AND r.createdAt BETWEEN :from AND :to GROUP BY r.status")
    List<Object[]> countGroupedByStatusForVendor(@Param("vendor") VendorProfile vendor, @Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);

    @Query("SELECT AVG(ds.estimatedRepairCost), COUNT(ds) FROM RepairBooking r JOIN r.submission ds WHERE r.createdAt BETWEEN :from AND :to AND ds.estimatedRepairCost > 0")
    List<Object[]> calculateAverageRepairCostAndSampleSize(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);
}
