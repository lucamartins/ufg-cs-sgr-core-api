package sgr.com.sgrcoreapi.controller;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sgr.com.sgrcoreapi.converters.table.TableConversionUtil;
import sgr.com.sgrcoreapi.infra.http.ApiResponse;
import sgr.com.sgrcoreapi.infra.http.NoDataApiResponse;
import sgr.com.sgrcoreapi.infra.http.PagedApiResponse;
import sgr.com.sgrcoreapi.service.table.CustomerTableService;
import sgr.com.sgrcoreapi.service.table.dto.AddTableRequest;
import sgr.com.sgrcoreapi.service.table.dto.TableDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tables")
public class TableController {
    private final CustomerTableService customerTableService;

    @PostMapping
    public ResponseEntity<NoDataApiResponse> createTable(@RequestBody @Valid AddTableRequest addTableRequest) {
        customerTableService.createTable(addTableRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new NoDataApiResponse(201, "Mesa criada com sucesso"));
    }

    @GetMapping
    public ResponseEntity<PagedApiResponse<TableDetails>> getTables(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "isAvailable", required = false) Boolean isAvailable) {
        Page<TableDetails> tablesPage = customerTableService.getTables(page, pageSize, isAvailable);
        var response = new PagedApiResponse<>(
                HttpStatus.OK.value(),
                tablesPage
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{tableId}")
    public ResponseEntity<ApiResponse<TableDetails>> getTableDetails(@PathVariable UUID tableId) {
        var table = customerTableService.findTableById(tableId);
        var tableDetails = TableConversionUtil.toTableDetails(table);

        var response = new ApiResponse<>(HttpStatus.OK.value(), tableDetails);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{tableId}")
    public ResponseEntity<NoDataApiResponse> deleteTable(@PathVariable UUID tableId) {
        customerTableService.deleteTable(tableId);
        var response = new NoDataApiResponse(
                HttpStatus.NO_CONTENT.value()
        );
        return ResponseEntity.ok(response);
    }
}
