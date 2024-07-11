package sgr.com.sgrcoreapi.service.order.dto;

import sgr.com.sgrcoreapi.domain.saleitem.SaleItemPriceCurrencyEnum;

import java.util.UUID;

public record OrderDetailsSaleItem(
        UUID id,
        String name,
        Double price,
        SaleItemPriceCurrencyEnum priceCurrency
) {
}
