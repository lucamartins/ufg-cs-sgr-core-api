package sgr.com.sgrcoreapi.service.saleitem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import sgr.com.sgrcoreapi.domain.saleitem.SaleItemPriceCurrencyEnum;

import java.util.List;

public record AddSaleItemRequest(
        @NotBlank
        String name,
        @NotNull
        Double price,
        @NotNull
        SaleItemPriceCurrencyEnum priceCurrency,
        @NotNull
        Boolean isSaleAvailable,
        @NotNull
        @Size(min = 1)
        List<AddSaleItemRequestStockItem> stockItems
) {
}
