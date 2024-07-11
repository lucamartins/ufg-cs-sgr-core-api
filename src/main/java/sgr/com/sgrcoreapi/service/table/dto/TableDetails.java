package sgr.com.sgrcoreapi.service.table.dto;

import java.util.UUID;

public record TableDetails(
        UUID id,
        boolean isAvailable
) {
}
