package com.agrismart.backend.dto.recommendation;

import java.time.Instant;

public record RecommendationItem(
        String id,
        String category,
        String severity,
        String title,
        String message,
        String reason,
        Instant generatedAt
) {
}
