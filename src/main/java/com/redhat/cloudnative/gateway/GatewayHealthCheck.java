package com.redhat.cloudnative.gateway;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Readiness
@ApplicationScoped
public class GatewayHealthCheck implements HealthCheck {

    @RestClient
    CatalogService catalogService;

    @RestClient
    InventoryService inventoryService;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.named("Gateway health check");
        try {
            catalogService.getCatalog().await().indefinitely();
            builder.withData("catalog", "UP");
        } catch (Exception e) {
            builder.withData("catalog", "DOWN").down();
            return builder.build();
        }
        try {
            inventoryService.getAvailability("test").await().indefinitely();
            builder.withData("inventory", "UP");
        } catch (Exception e) {
            builder.withData("inventory", "DOWN").down();
            return builder.build();
        }
        return builder.up().build();
    }
}
