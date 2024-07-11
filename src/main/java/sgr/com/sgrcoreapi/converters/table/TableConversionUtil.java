package sgr.com.sgrcoreapi.converters.table;

import lombok.experimental.UtilityClass;
import sgr.com.sgrcoreapi.domain.table.Table;
import sgr.com.sgrcoreapi.service.table.dto.TableDetails;

@UtilityClass
public class TableConversionUtil {
    public static TableDetails toTableDetails(Table table) {
        return new TableDetails(table.getId(), table.isAvailable());
    }
}
