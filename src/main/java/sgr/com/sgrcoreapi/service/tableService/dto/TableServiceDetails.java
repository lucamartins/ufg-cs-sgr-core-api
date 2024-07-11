package sgr.com.sgrcoreapi.service.tableService.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import sgr.com.sgrcoreapi.domain.tableservice.TableServiceStatus;
import sgr.com.sgrcoreapi.service.table.dto.TableDetails;
import sgr.com.sgrcoreapi.service.user.dto.TableServiceResponsibleUser;

public record TableServiceDetails(
        UUID id,
        TableServiceResponsibleUser waiter,
        TableDetails table,
        TableServiceStatus status,
        Double paidAmount,
        LocalDateTime startedAt
) {
}
