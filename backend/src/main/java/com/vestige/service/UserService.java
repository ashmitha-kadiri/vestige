package com.vestige.service;

import com.vestige.dto.request.LanguageUpdateRequest;
import com.vestige.dto.response.UserSummaryResponse;
import com.vestige.exception.ForbiddenException;
import com.vestige.exception.ResourceNotFoundException;
import com.vestige.model.User;
import com.vestige.repository.UserRepository;
import com.vestige.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserSummaryResponse updateLanguage(LanguageUpdateRequest request) {
        SecurityUtils.assertActive();
        UUID userId = SecurityUtils.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (!user.isActive()) {
            throw new ForbiddenException("Suspended accounts cannot update profile settings.");
        }

        user.setPreferredLang(request.getLanguage());
        user.setUpdatedAt(OffsetDateTime.now());
        User saved = userRepository.save(user);

        logger.info("Updated preferred language to '{}' for user {}", request.getLanguage(), userId);
        return UserSummaryResponse.fromEntity(saved);
    }
}
