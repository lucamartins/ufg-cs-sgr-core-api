package sgr.com.sgrcoreapi.service.order.dto;

import java.util.UUID;
import sgr.com.sgrcoreapi.domain.order.Order;
import sgr.com.sgrcoreapi.domain.saleitem.SaleItem;
import sgr.com.sgrcoreapi.domain.saleitem.SaleItemPriceCurrencyEnum;

public record ClosingTableServiceOrderDetails(
        UUID id,
        UUID waiterId,
        Double price,
        SaleItemPriceCurrencyEnum priceCurrency
) {
    public ClosingTableServiceOrderDetails(Order order) {
        this(
                order.getId(),
                order.getWaiter().getId(),
                order.getSaleItems().stream().mapToDouble(SaleItem::getPrice).sum(),
                order.getSaleItems().get(0).getPriceCurrency()
        );
    }
}