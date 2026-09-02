package com.vestige.service;

import com.vestige.dto.request.LanguageUpdateRequest;
import com.vestige.dto.response.UserSummaryResponse;
import com.vestige.model.User;
import com.vestige.model.enums.PreferredLanguage;
import com.vestige.model.enums.UserRole;
import com.vestige.repository.UserRepository;
import com.vestige.security.UserPrincipal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setEmail("language_patron_" + System.currentTimeMillis() + "@vestige.test");
        testUser.setFullName("Archival Patron");
        testUser.setPasswordHash("$2a$10$hashedPasswordHere");
        testUser.setPhone("+919876543210");
        testUser.setRole(UserRole.USER);
        testUser.setPreferredLang(PreferredLanguage.en);
        testUser.setIsActive(true);
        testUser = userRepository.save(testUser);

        UserPrincipal principal = UserPrincipal.fromEntity(testUser);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should successfully update preferred language to Tamil, Telugu, and Japanese")
    void testUpdateLanguageSuccess() {
        // Update to Telugu
        LanguageUpdateRequest teRequest = new LanguageUpdateRequest(PreferredLanguage.te);
        UserSummaryResponse responseTe = userService.updateLanguage(teRequest);
        assertThat(responseTe.getPreferredLanguage()).isEqualTo(PreferredLanguage.te);

        User refreshed = userRepository.findById(testUser.getId()).orElseThrow();
        assertThat(refreshed.getPreferredLanguage()).isEqualTo(PreferredLanguage.te);

        // Update to Japanese
        LanguageUpdateRequest jaRequest = new LanguageUpdateRequest(PreferredLanguage.ja);
        UserSummaryResponse responseJa = userService.updateLanguage(jaRequest);
        assertThat(responseJa.getPreferredLanguage()).isEqualTo(PreferredLanguage.ja);

        refreshed = userRepository.findById(testUser.getId()).orElseThrow();
        assertThat(refreshed.getPreferredLanguage()).isEqualTo(PreferredLanguage.ja);
    }
}
