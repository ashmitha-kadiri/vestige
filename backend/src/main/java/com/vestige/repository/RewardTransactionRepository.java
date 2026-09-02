package com.vestige.repository;

import com.vestige.model.RewardTransaction;
import com.vestige.model.enums.RewardSourceType;
import com.vestige.model.enums.RewardTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RewardTransactionRepository extends JpaRepository<RewardTransaction, UUID> {
    List<RewardTransaction> findByAccountIdOrderByCreatedAtDesc(UUID accountId);

    @Query("SELECT t FROM RewardTransaction t WHERE t.account.user.id = :userId ORDER BY t.createdAt DESC")
    List<RewardTransaction> findByUserIdOrderByCreatedAtDesc(@Param("userId") UUID userId);

    List<RewardTransaction> findByReferenceIdAndSource(UUID referenceId, RewardSourceType source);

    boolean existsByReferenceIdAndSource(UUID referenceId, RewardSourceType source);

    long countByCreatedAtBetween(OffsetDateTime from, OffsetDateTime to);

    @Query("SELECT COALESCE(SUM(t.points), 0) FROM RewardTransaction t WHERE t.transactionType = :type AND t.createdAt BETWEEN :from AND :to")
    Long sumPointsByTypeAndCreatedAtBetween(@Param("type") RewardTransactionType type, @Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);

    @Query("SELECT t.source, COALESCE(SUM(t.points), 0) FROM RewardTransaction t WHERE t.transactionType = 'EARNED' AND t.createdAt BETWEEN :from AND :to GROUP BY t.source")
    List<Object[]> sumEarnedPointsGroupedBySource(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);
}
