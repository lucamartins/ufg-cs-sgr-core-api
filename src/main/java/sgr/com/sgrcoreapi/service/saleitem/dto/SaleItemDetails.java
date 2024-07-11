package sgr.com.sgrcoreapi.service.saleitem.dto;

import sgr.com.sgrcoreapi.domain.saleitem.SaleItemPriceCurrencyEnum;

import java.util.List;
import java.util.UUID;

public record SaleItemDetails(
        UUID id,
        String name,
        Double price,
        SaleItemPriceCurrencyEnum priceCurrency,
        Boolean isSaleAvailable,
        List<StockItemAssociationDetails> stockItems
) {
}
