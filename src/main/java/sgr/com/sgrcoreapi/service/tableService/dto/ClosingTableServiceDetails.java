package sgr.com.sgrcoreapi.service.tableService.dto;

import java.util.List;
import sgr.com.sgrcoreapi.domain.order.Order;

public record ClosingTableServiceDetails(
        List<Order> orders,
        double dueAmount
) {
}
