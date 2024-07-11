package sgr.com.sgrcoreapi.service.user.dto;

import java.util.UUID;

public record TableServiceResponsibleUser(
        UUID waiterId,
        String name
) {
}
