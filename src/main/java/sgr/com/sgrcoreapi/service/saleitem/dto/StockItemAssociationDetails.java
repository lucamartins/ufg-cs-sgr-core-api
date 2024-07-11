package sgr.com.sgrcoreapi.service.saleitem.dto;

import sgr.com.sgrcoreapi.domain.saleitem.SaleItemStockItem;
import sgr.com.sgrcoreapi.service.stockitem.dto.StockItemDetails;

import java.util.UUID;

public record StockItemAssociationDetails(
        UUID id,
        StockItemDetails stockItem,
        Double fractionalQuantity,
        Long wholeQuantity
) {
    public StockItemAssociationDetails(SaleItemStockItem saleItemStockItem) {
        this(
                saleItemStockItem.getId(),
                new StockItemDetails(saleItemStockItem.getStockItem()),
                saleItemStockItem.getFractionalQuantity(),
                saleItemStockItem.getWholeQuantity()
        );
    }
}
