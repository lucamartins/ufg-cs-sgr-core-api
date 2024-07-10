package sgr.com.sgrcoreapi.service.stockitem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record AddStockItemRequest(
        @NotBlank
        @Length(min = 3, max = 255)
        String name,
        @NotNull
        Boolean allowFractionalQuantity,
        Double fractionalQuantity,
        Long wholeQuantity
) {
}
