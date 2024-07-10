package sgr.com.sgrcoreapi.service.stockitem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record AddStockItemRequest(
        @NotBlank
        @Length(min = 3, max = 255)
        String name,
        @NotNull
        Boolean allowFractionalQuantity,
        @Positive
        Double fractionalQuantity,
        @Positive
        Long wholeQuantity
) {
}
