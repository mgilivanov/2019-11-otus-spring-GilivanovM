package ru.mgilivanov.project.health;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.mgilivanov.project.service.EodService;
import ru.mgilivanov.project.service.PayDocumentService;
@Component
@AllArgsConstructor
public class PayDocHealthIndicator implements HealthIndicator {

    private final PayDocumentService payDocumentService;
    private final EodService eodService;

    @Override
    public Health health() {
        long count = payDocumentService.countByEodDate(eodService.getPrevEodDate());
        if (count > 0) {
            return Health.up().withDetail("message", "Paydocs count is OK").build();
        }
        return Health.outOfService().withDetail("message", "Alert! Paydocs count on yesterday is ZERO.").build();
    }
}
