package com.vestige.dto.response;

public class LoginResponse {

    private String token;
    private AuthMeResponse profile;

    public LoginResponse() {
    }

    public LoginResponse(String token, AuthMeResponse profile) {
        this.token = token;
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthMeResponse getProfile() {
        return profile;
    }

    public void setProfile(AuthMeResponse profile) {
        this.profile = profile;
    }
}
