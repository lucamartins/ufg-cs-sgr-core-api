package sgr.com.sgrcoreapi.service.stockitem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import sgr.com.sgrcoreapi.domain.stockitem.StockItemMeasurementUnitEnum;

public record AddStockItemRequest(
        @NotBlank
        @Length(min = 3, max = 255)
        String name,
        @NotNull
        StockItemMeasurementUnitEnum measurementUnit,
        @NotNull
        Boolean allowFractionalQuantity,
        @Positive
        Double fractionalQuantity,
        @Positive
        Long wholeQuantity
) {
}
