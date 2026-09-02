package com.vestige.security;

import com.vestige.exception.AccountSuspendedException;
import com.vestige.exception.ForbiddenException;
import com.vestige.exception.UnauthorizedException;
import com.vestige.exception.VendorNotVerifiedException;
import com.vestige.model.VendorProfile;
import com.vestige.model.enums.UserRole;
import com.vestige.model.enums.VendorVerificationStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Optional<UserPrincipal> getCurrentUserPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserPrincipal principal) {
            return Optional.of(principal);
        }
        return Optional.empty();
    }

    public static UserPrincipal requireCurrentUser() {
        return getCurrentUserPrincipal()
                .orElseThrow(() -> new UnauthorizedException("Authentication token required for this resource"));
    }

    public static UUID getCurrentUserId() {
        return requireCurrentUser().getId();
    }

    public static UserRole getCurrentUserRole() {
        return requireCurrentUser().getRole();
    }

    public static void assertActive() {
        UserPrincipal principal = requireCurrentUser();
        if (!principal.isActive()) {
            throw new AccountSuspendedException("Account has been suspended. Please contact VESTIGE platform support.");
        }
    }

    public static void assertOwnershipOrAdmin(UUID resourceOwnerId) {
        UserPrincipal principal = requireCurrentUser();
        assertActive();

        if (principal.getRole() == UserRole.ADMIN) {
            return; // Platform Admins have overarching supervisory authority
        }

        if (resourceOwnerId == null || !principal.getId().equals(resourceOwnerId)) {
            throw new ForbiddenException("Access denied: You do not have ownership of this archival record");
        }
    }

    public static void assertVendorVerified(VendorProfile vendorProfile) {
        if (vendorProfile == null) {
            throw new ForbiddenException("Vendor profile does not exist");
        }
        if (vendorProfile.getVerificationStatus() != VendorVerificationStatus.VERIFIED) {
            throw new VendorNotVerifiedException("Vendor account is currently pending administrative verification or has been rejected");
        }
    }
}
