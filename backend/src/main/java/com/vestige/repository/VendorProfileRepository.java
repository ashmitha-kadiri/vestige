package com.vestige.repository;

import com.vestige.model.VendorProfile;
import com.vestige.model.enums.VendorVerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendorProfileRepository extends JpaRepository<VendorProfile, UUID> {
    @Query("SELECT v FROM VendorProfile v WHERE v.user.id = :userId")
    Optional<VendorProfile> findByUserId(@Param("userId") UUID userId);
    List<VendorProfile> findByVerificationStatus(VendorVerificationStatus status);

    @Query("SELECT v FROM VendorProfile v WHERE v.verificationStatus = :status")
    List<VendorProfile> findAllVerified(@Param("status") VendorVerificationStatus status);

    long countByVerificationStatus(VendorVerificationStatus status);
}
