package sgr.com.sgrcoreapi.service.stockitem.saleitem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record AddSaleItemRequestStockItem(
        @NotNull
        UUID stockItemId,
        @Positive
        Double fractionalQuantity,
        @Positive
        Long wholeQuantity
) {
}
