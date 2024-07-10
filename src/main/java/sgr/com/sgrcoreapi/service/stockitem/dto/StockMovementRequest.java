package sgr.com.sgrcoreapi.service.stockitem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockMovementRequest(
        @NotNull
        StockMovementTypeEnum type,
        @Positive
        Double fractionalQuantity,
        @Positive
        Long wholeQuantity
) {
}
