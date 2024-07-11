package sgr.com.sgrcoreapi.service.stockitem.saleitem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
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
        @Length(min = 1)
        List<AddSaleItemRequestStockItem> stockItems
) {
}
