package sgr.com.sgrcoreapi.converters.tableservice;

import lombok.experimental.UtilityClass;
import sgr.com.sgrcoreapi.domain.tableservice.TableService;
import sgr.com.sgrcoreapi.service.table.dto.TableDetails;
import sgr.com.sgrcoreapi.service.tableService.dto.ClosingTableServiceDetails;
import sgr.com.sgrcoreapi.service.tableService.dto.TableServiceDetails;
import sgr.com.sgrcoreapi.service.user.dto.TableServiceResponsibleUser;

@UtilityClass
public class TableServiceConversionUtil {
    public static TableServiceDetails toTableServiceDetails(TableService tableService, TableDetails tableDetails, TableServiceResponsibleUser tableServiceResponsibleUser) {
        return new TableServiceDetails(
                tableService.getId(),
                tableServiceResponsibleUser,
                tableDetails,
                tableService.getStatus(),
                tableService.getPaidAmount(),
                tableService.getStartedAt()
        );
    }

    public static ClosingTableServiceDetails toClosingTableServiceDetails(TableService tableService, double dueAmount) {
        return new ClosingTableServiceDetails(
                tableService.getOrders(),
                dueAmount
        );
    }
}
