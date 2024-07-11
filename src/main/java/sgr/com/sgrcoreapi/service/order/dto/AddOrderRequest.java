package sgr.com.sgrcoreapi.service.order.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AddOrderRequest(
        @NotNull
        UUID tableServiceId,
        @NotNull
        UUID waiterId,
        @NotNull
        List<UUID> saleItemsIds
) {
}
