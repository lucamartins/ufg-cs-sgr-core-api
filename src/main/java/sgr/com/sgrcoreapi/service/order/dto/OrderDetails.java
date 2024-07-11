package sgr.com.sgrcoreapi.service.order.dto;

import sgr.com.sgrcoreapi.domain.order.Order;
import sgr.com.sgrcoreapi.domain.order.OrderStatusEnum;
import sgr.com.sgrcoreapi.domain.saleitem.SaleItem;
import sgr.com.sgrcoreapi.domain.saleitem.SaleItemPriceCurrencyEnum;

import java.util.List;
import java.util.UUID;

public record OrderDetails(
        UUID id,
        UUID tableServiceId,
        UUID waiterId,
        OrderStatusEnum status,
        List<OrderDetailsSaleItem> saleItems,
        Double price,
        SaleItemPriceCurrencyEnum priceCurrency
) {
    public OrderDetails(Order order) {
        this(
                order.getId(),
                order.getTableService().getId(),
                order.getWaiter().getId(),
                order.getStatus(),
                order.getSaleItems().stream().map(OrderDetailsSaleItem::new).toList(),
                order.getSaleItems().stream().mapToDouble(SaleItem::getPrice).sum(),
                order.getSaleItems().get(0).getPriceCurrency()
        );
    }
}
