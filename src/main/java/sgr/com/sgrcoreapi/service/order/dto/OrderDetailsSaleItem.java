package sgr.com.sgrcoreapi.service.order.dto;

import sgr.com.sgrcoreapi.domain.saleitem.SaleItem;

import java.util.UUID;

public record OrderDetailsSaleItem(
        UUID id,
        String name,
        Double price
) {
    public OrderDetailsSaleItem (SaleItem saleItem) {
        this(
                saleItem.getId(),
                saleItem.getName(),
                saleItem.getPrice()
        );
    }
}
