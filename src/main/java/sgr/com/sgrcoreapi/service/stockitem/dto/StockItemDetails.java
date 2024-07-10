package sgr.com.sgrcoreapi.service.stockitem.dto;

import sgr.com.sgrcoreapi.domain.stockitem.StockItem;

import java.util.UUID;

public record StockItemDetails(
        UUID id,
        String name,
        Boolean allowFractionalQuantity,
        Double fractionalQuantity,
        Long wholeQuantity
) {
    public StockItemDetails(StockItem stockItem) {
        this(
                stockItem.getId(),
                stockItem.getName(),
                stockItem.getAllowFractionalQuantity(),
                stockItem.getFractionalQuantity(),
                stockItem.getWholeQuantity()
        );
    }
}
