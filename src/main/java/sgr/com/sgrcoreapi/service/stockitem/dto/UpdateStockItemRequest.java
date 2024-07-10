package sgr.com.sgrcoreapi.service.stockitem.dto;

import jakarta.validation.constraints.NotEmpty;

public record UpdateStockItemRequest(
        @NotEmpty
        String name
) {
}
