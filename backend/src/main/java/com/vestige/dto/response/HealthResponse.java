package com.vestige.dto.response;

import java.time.Instant;

/**
 * Health check status payload.
 */
public class HealthResponse {
    private String status;
    private String service;
    private String environment;
    private String version;
    private String timestamp;

    private java.util.Map<String, String> components;
    private long uptimeSeconds;

    public HealthResponse() {
        this.timestamp = Instant.now().toString();
    }

    public HealthResponse(String status, String service, String environment, String version) {
        this.status = status;
        this.service = service;
        this.environment = environment;
        this.version = version;
        this.timestamp = Instant.now().toString();
    }

    public HealthResponse(String status, String service, String environment, String version, java.util.Map<String, String> components, long uptimeSeconds) {
        this.status = status;
        this.service = service;
        this.environment = environment;
        this.version = version;
        this.components = components;
        this.uptimeSeconds = uptimeSeconds;
        this.timestamp = Instant.now().toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public java.util.Map<String, String> getComponents() {
        return components;
    }

    public void setComponents(java.util.Map<String, String> components) {
        this.components = components;
    }

    public long getUptimeSeconds() {
        return uptimeSeconds;
    }

    public void setUptimeSeconds(long uptimeSeconds) {
        this.uptimeSeconds = uptimeSeconds;
    }
}
