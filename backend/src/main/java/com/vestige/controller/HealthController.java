package com.vestige.controller;

import com.vestige.dto.response.ApiResponse;
import com.vestige.dto.response.HealthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Health check controller for verifying VESTIGE backend readiness and uptime.
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    @Value("${spring.application.name:vestige-backend}")
    private String appName;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    private final DataSource dataSource;

    public HealthController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<HealthResponse>> checkHealth() {
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
        HealthResponse health = new HealthResponse(
                "UP",
                appName,
                activeProfile,
                "0.0.1-SNAPSHOT"
        );
        health.setUptimeSeconds(uptime);
        return ResponseEntity.ok(ApiResponse.success("Vestige Backend Service is running", health));
    }

    @GetMapping("/health/readiness")
    public ResponseEntity<ApiResponse<HealthResponse>> checkReadiness() {
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
        Map<String, String> components = new LinkedHashMap<>();
        boolean dbHealthy = true;

        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(2)) {
                components.put("database", "UP");
            } else {
                components.put("database", "DOWN");
                dbHealthy = false;
            }
        } catch (Exception e) {
            components.put("database", "UNAVAILABLE");
            dbHealthy = false;
        }

        components.put("decision_engine", "UP");
        components.put("auth_filter", "UP");

        String overallStatus = dbHealthy ? "READY" : "DEGRADED";
        HealthResponse health = new HealthResponse(
                overallStatus,
                appName,
                activeProfile,
                "0.0.1-SNAPSHOT",
                components,
                uptime
        );

        if (dbHealthy) {
            return ResponseEntity.ok(ApiResponse.success("Vestige Platform is ready to process traffic", health));
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ApiResponse<>(false, "Vestige Platform is experiencing component degradation", health));
        }
    }
}
