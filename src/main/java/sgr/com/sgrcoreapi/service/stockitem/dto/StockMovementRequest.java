package sgr.com.sgrcoreapi.service.stockitem.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StockMovementRequest(
        @NotNull
        UUID stockItemId,
        @NotNull
        StockMovementTypeEnum type,
        Double fractionalQuantity,
        Long wholeQuantity
) {
}
