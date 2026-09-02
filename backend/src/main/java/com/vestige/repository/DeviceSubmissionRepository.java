package com.vestige.repository;

import com.vestige.model.DeviceSubmission;
import com.vestige.model.User;
import com.vestige.model.enums.EngineRecommendationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceSubmissionRepository extends JpaRepository<DeviceSubmission, UUID> {
    List<DeviceSubmission> findByUserIdOrderByCreatedAtDesc(UUID userId);

    long countByCreatedAtBetween(OffsetDateTime from, OffsetDateTime to);

    long countByEngineRecommendationAndCreatedAtBetween(EngineRecommendationType recommendation, OffsetDateTime from, OffsetDateTime to);

    long countByUserAndCreatedAtBetween(User user, OffsetDateTime from, OffsetDateTime to);

    long countByUserAndEngineRecommendationAndCreatedAtBetween(User user, EngineRecommendationType recommendation, OffsetDateTime from, OffsetDateTime to);

    @Query("SELECT d.deviceType, COUNT(d) FROM DeviceSubmission d WHERE d.createdAt BETWEEN :from AND :to GROUP BY d.deviceType")
    List<Object[]> countGroupedByCategory(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);

    @Query("SELECT d.condition, COUNT(d) FROM DeviceSubmission d WHERE d.createdAt BETWEEN :from AND :to GROUP BY d.condition")
    List<Object[]> countGroupedByCondition(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);

    @Query("SELECT LOWER(d.brand), COUNT(d) FROM DeviceSubmission d WHERE d.createdAt BETWEEN :from AND :to GROUP BY LOWER(d.brand) ORDER BY COUNT(d) DESC")
    List<Object[]> countGroupedByBrand(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);

    @Query("SELECT d.engineConfidence, COUNT(d) FROM DeviceSubmission d WHERE d.createdAt BETWEEN :from AND :to GROUP BY d.engineConfidence")
    List<Object[]> countGroupedByConfidence(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);

    @Query("SELECT AVG(d.engineScore) FROM DeviceSubmission d WHERE d.createdAt BETWEEN :from AND :to AND d.engineScore IS NOT NULL")
    Double calculateAverageEngineScore(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);
}
