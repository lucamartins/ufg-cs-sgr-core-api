package sgr.com.sgrcoreapi.service.tableService.dto;

import java.util.List;
import sgr.com.sgrcoreapi.domain.order.Order;
import sgr.com.sgrcoreapi.service.order.dto.ClosingTableServiceOrderDetails;

public record ClosingTableServiceDetails(
        List<ClosingTableServiceOrderDetails> orders,
        double dueAmount
) {
}
