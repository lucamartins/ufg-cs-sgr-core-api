package sgr.com.sgrcoreapi.service.saleitem.dto;

import sgr.com.sgrcoreapi.service.stockitem.dto.StockItemDetails;

import java.util.UUID;

public record StockItemAssociationDetails(
        UUID id,
        StockItemDetails stockItem,
        Double fractionalQuantity,
        Long wholeQuantity
) {
}
