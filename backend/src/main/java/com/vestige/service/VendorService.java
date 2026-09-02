package com.vestige.service;

import com.vestige.dto.response.VendorSummaryResponse;
import com.vestige.model.VendorProfile;
import com.vestige.model.enums.VendorVerificationStatus;
import com.vestige.repository.VendorProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VendorService {

    private final VendorProfileRepository vendorProfileRepository;

    public VendorService(VendorProfileRepository vendorProfileRepository) {
        this.vendorProfileRepository = vendorProfileRepository;
    }

    @Transactional(readOnly = true)
    public List<VendorSummaryResponse> getVendors(String serviceType) {
        // Optimized: fetch only verified vendors from DB instead of loading all unverified/rejected records
        List<VendorProfile> vendors = vendorProfileRepository.findByVerificationStatus(VendorVerificationStatus.VERIFIED);
        List<VendorSummaryResponse> responses = new ArrayList<>();
        for (VendorProfile v : vendors) {
            if (serviceType == null || serviceType.isBlank() ||
                    (v.getServiceTypes() != null && v.getServiceTypes().stream().anyMatch(s -> s.equalsIgnoreCase(serviceType)))) {
                responses.add(mapToResponse(v));
            }
        }
        return responses;
    }

    @Transactional(readOnly = true)
    public VendorSummaryResponse getVendorById(UUID id) {
        VendorProfile v = vendorProfileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found: " + id));
        return mapToResponse(v);
    }

    private VendorSummaryResponse mapToResponse(VendorProfile v) {
        VendorSummaryResponse res = new VendorSummaryResponse();
        res.setId(v.getId());
        if (v.getUser() != null) {
            res.setUserId(v.getUser().getId());
        }
        res.setBusinessName(v.getBusinessName());
        res.setBusinessType(v.getBusinessType());
        res.setAddress(v.getAddress());
        res.setCity(v.getCity());
        res.setState(v.getState());
        res.setPincode(v.getPincode());
        res.setWhatsappNumber(v.getWhatsappNumber());
        res.setServiceTypes(v.getServiceTypes());
        res.setDeviceCategories(v.getDeviceCategories());
        res.setVerificationStatus(v.getVerificationStatus());
        res.setRatingAvg(v.getRatingAvg());
        res.setRatingCount(v.getRatingCount());
        return res;
    }
}
