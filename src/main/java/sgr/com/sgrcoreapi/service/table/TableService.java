package sgr.com.sgrcoreapi.service.table;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sgr.com.sgrcoreapi.converters.table.TableConversionUtil;
import sgr.com.sgrcoreapi.domain.table.CustomerTable;
import sgr.com.sgrcoreapi.domain.table.TableRepository;
import sgr.com.sgrcoreapi.infra.exception.custom.InternalErrorException;
import sgr.com.sgrcoreapi.infra.exception.custom.NotFoundException;
import sgr.com.sgrcoreapi.service.table.dto.AddTableRequest;
import sgr.com.sgrcoreapi.service.table.dto.TableDetails;

@Service
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepository;

    public void createTable(AddTableRequest addTableRequest) {
        CustomerTable customerTable = new CustomerTable(addTableRequest);
        tableRepository.save(customerTable);
    }

    public CustomerTable findTableById(UUID tableId) {
        return tableRepository.findById(tableId).orElseThrow(() -> new NotFoundException("Mesa não encontrada"));
    }

    public void deleteTable(UUID tableId) {
        CustomerTable customerTable = tableRepository.findByIdAndIsDeletedFalse(tableId)
                .orElseThrow(() -> new NotFoundException("Mesa não encontrada"));

        if(!customerTable.isAvailable()) {
            throw new InternalErrorException("Mesa não pode ser deletada pois está em uso");
        }
        // TODO validar se atendimento está aberto
        customerTable.setDeleted(true);
        tableRepository.save(customerTable);
    }

    public Page<TableDetails> getTables(int page, int pageSize, Boolean isAvailable) {
        Pageable pageable = PageRequest.of(page, pageSize);

        var tablesPage = isAvailable != null
                ? tableRepository.findByIsAvailableAndIsDeletedFalse(isAvailable, pageable)
                : tableRepository.findByIsDeletedFalse(pageable);

        return tablesPage.map(TableConversionUtil::toTableDetails);
    }
}