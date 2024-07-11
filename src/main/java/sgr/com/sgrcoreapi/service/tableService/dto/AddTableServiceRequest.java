package sgr.com.sgrcoreapi.service.tableService.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AddTableServiceRequest(
        @NotNull
        UUID waiterId,
        @NotNull
        UUID tableId
) {
}
