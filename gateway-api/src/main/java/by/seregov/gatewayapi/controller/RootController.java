package by.seregov.gatewayapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> gatewayInfo() {
        return ResponseEntity.ok(Map.of(
                "message", "API Gateway is running",
                "services", Map.of(
                        "user-service", "/api/users/**",
                        "notification-service", "/api/email/**"
                ),
                "actuator", "/actuator",
                "status", "UP"
        ));
    }

}