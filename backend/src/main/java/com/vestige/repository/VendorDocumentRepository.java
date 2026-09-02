package com.vestige.repository;

import com.vestige.model.VendorDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VendorDocumentRepository extends JpaRepository<VendorDocument, UUID> {
    List<VendorDocument> findByVendorIdOrderByUploadedAtDesc(UUID vendorId);
}
