package com.agrismart.backend.dto.recommendation;

import java.util.List;
import java.util.UUID;

public record RecommendationResponse(
        UUID farmId,
        String prototypeNotice,
        List<RecommendationItem> recommendations
) {
}
