package com.vestige.security;

import com.vestige.exception.AccountSuspendedException;
import com.vestige.exception.ForbiddenException;
import com.vestige.exception.VendorNotVerifiedException;
import com.vestige.model.VendorProfile;
import com.vestige.model.enums.PreferredLanguage;
import com.vestige.model.enums.UserRole;
import com.vestige.model.enums.VendorVerificationStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUtilsTest {

    private UUID userId;
    private UUID otherUserId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        otherUserId = UUID.randomUUID();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void authenticateAs(UUID id, UserRole role, boolean active) {
        UserPrincipal principal = new UserPrincipal(id, "test@vestige.internal", "Test User", role, PreferredLanguage.en, active);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @DisplayName("assertOwnershipOrAdmin succeeds when caller is resource owner")
    void ownershipSucceedsForOwner() {
        authenticateAs(userId, UserRole.USER, true);
        assertDoesNotThrow(() -> SecurityUtils.assertOwnershipOrAdmin(userId));
    }

    @Test
    @DisplayName("assertOwnershipOrAdmin throws ForbiddenException when caller is not owner")
    void ownershipFailsForNonOwner() {
        authenticateAs(userId, UserRole.USER, true);
        assertThrows(ForbiddenException.class, () -> SecurityUtils.assertOwnershipOrAdmin(otherUserId));
    }

    @Test
    @DisplayName("assertOwnershipOrAdmin succeeds for ADMIN even when not owner")
    void ownershipSucceedsForAdmin() {
        authenticateAs(userId, UserRole.ADMIN, true);
        assertDoesNotThrow(() -> SecurityUtils.assertOwnershipOrAdmin(otherUserId));
    }

    @Test
    @DisplayName("assertActive throws AccountSuspendedException when user is inactive")
    void assertActiveThrowsWhenSuspended() {
        authenticateAs(userId, UserRole.USER, false);
        assertThrows(AccountSuspendedException.class, SecurityUtils::assertActive);
    }

    @Test
    @DisplayName("assertVendorVerified throws VendorNotVerifiedException when status is PENDING or REJECTED")
    void assertVendorVerifiedGating() {
        VendorProfile pendingVendor = new VendorProfile();
        pendingVendor.setVerificationStatus(VendorVerificationStatus.PENDING);

        assertThrows(VendorNotVerifiedException.class, () -> SecurityUtils.assertVendorVerified(pendingVendor));

        VendorProfile verifiedVendor = new VendorProfile();
        verifiedVendor.setVerificationStatus(VendorVerificationStatus.VERIFIED);

        assertDoesNotThrow(() -> SecurityUtils.assertVendorVerified(verifiedVendor));
    }
}
