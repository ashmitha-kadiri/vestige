package com.vestige.dto.request;

import com.vestige.model.enums.PreferredLanguage;
import jakarta.validation.constraints.NotNull;

public class LanguageUpdateRequest {

    @NotNull(message = "Preferred language is required")
    private PreferredLanguage language;

    public LanguageUpdateRequest() {}

    public LanguageUpdateRequest(PreferredLanguage language) {
        this.language = language;
    }

    public PreferredLanguage getLanguage() {
        return language;
    }

    public void setLanguage(PreferredLanguage language) {
        this.language = language;
    }
}
