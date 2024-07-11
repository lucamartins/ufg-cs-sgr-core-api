package sgr.com.sgrcoreapi.service.table;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sgr.com.sgrcoreapi.converters.table.TableConversionUtil;
import sgr.com.sgrcoreapi.domain.table.Table;
import sgr.com.sgrcoreapi.domain.table.TableRepository;
import sgr.com.sgrcoreapi.infra.exception.custom.CustomExceptionBody;
import sgr.com.sgrcoreapi.infra.exception.custom.InternalErrorException;
import sgr.com.sgrcoreapi.infra.exception.custom.NotFoundException;
import sgr.com.sgrcoreapi.service.table.dto.AddTableRequest;
import sgr.com.sgrcoreapi.service.table.dto.TableDetails;

@Service
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepository;

    public void createTable(AddTableRequest addTableRequest) {
        Table table = new Table(addTableRequest);
        tableRepository.save(table);
    }

    public Table findTableById(UUID tableId) {
        return tableRepository.findById(tableId).orElseThrow(() -> new NotFoundException("Mesa não encontrada"));
    }

    public void deleteTable(UUID tableId) {
        Table table = tableRepository.findByIdAndIsDeletedFalse(tableId)
                .orElseThrow(() -> new NotFoundException("Mesa não encontrada"));

        if(!table.isAvailable()) {
            throw new InternalErrorException("Mesa não pode ser deletada pois está em uso");
        }
        // TODO validar se atendimento está aberto
        table.setDeleted(true);
        tableRepository.save(table);
    }

    public Page<TableDetails> getTables(int page, int pageSize, Boolean isAvailable) {
        Pageable pageable = PageRequest.of(page, pageSize);

        var tablesPage = isAvailable != null
                ? tableRepository.findByIsAvailableAndIsDeletedFalse(isAvailable, pageable)
                : tableRepository.findByIsDeletedFalse(pageable);

        return tablesPage.map(TableConversionUtil::toTableDetails);
    }
}
