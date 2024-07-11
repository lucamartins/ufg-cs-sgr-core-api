package sgr.com.sgrcoreapi.service.saleitem.dto;

import org.hibernate.validator.constraints.Length;

public record UpdateSaleItemRequest(
        @Length(min = 3, max = 100)
        String name,
        Boolean isAvailable
) {
}
