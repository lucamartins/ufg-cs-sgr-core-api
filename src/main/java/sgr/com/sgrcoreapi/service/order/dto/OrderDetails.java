package sgr.com.sgrcoreapi.service.order.dto;

import sgr.com.sgrcoreapi.domain.order.OrderStatusEnum;
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
}
