package sgr.com.sgrcoreapi.converters.table;

import lombok.experimental.UtilityClass;
import sgr.com.sgrcoreapi.domain.table.CustomerTable;
import sgr.com.sgrcoreapi.service.table.dto.TableDetails;

@UtilityClass
public class TableConversionUtil {
    public static TableDetails toTableDetails(CustomerTable customerTable) {
        return new TableDetails(customerTable.getId(), customerTable.isAvailable());
    }
}
