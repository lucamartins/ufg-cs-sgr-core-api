package sgr.com.sgrcoreapi.service.table.dto;

import jakarta.validation.constraints.NotNull;

public record AddTableRequest(
        @NotNull
        Boolean isAvailable) {
}
