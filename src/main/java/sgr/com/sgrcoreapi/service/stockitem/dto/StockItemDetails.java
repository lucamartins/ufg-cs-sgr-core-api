package sgr.com.sgrcoreapi.service.stockitem.dto;

public record StockItemDetails(
        String name,
        Boolean allowFractionalQuantity,
        Double fractionalQuantity,
        Long wholeQuantity
) {
}
