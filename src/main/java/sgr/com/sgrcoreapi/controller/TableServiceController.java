package sgr.com.sgrcoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sgr.com.sgrcoreapi.domain.tableservice.TableServiceStatus;
import sgr.com.sgrcoreapi.infra.http.ApiResponse;
import sgr.com.sgrcoreapi.infra.http.NoDataApiResponse;
import sgr.com.sgrcoreapi.infra.http.PagedApiResponse;
import sgr.com.sgrcoreapi.service.tableService.TableServiceService;
import sgr.com.sgrcoreapi.service.tableService.dto.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/table-service")
public class TableServiceController {

    private final TableServiceService tableServService;

    @Transactional
    @PostMapping
    public ResponseEntity<NoDataApiResponse> createTableService(@RequestBody @Valid AddTableServiceRequest addTableServiceRequest) {
        tableServService.createTableService(addTableServiceRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new NoDataApiResponse(201, "Table service created successfully"));
    }

    @GetMapping
    public ResponseEntity<PagedApiResponse<TableServiceDetails>> getTableServices(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "status", required = false) TableServiceStatus status,
            @RequestParam(name = "waiterId", required = false) UUID waiterId
    ) {
        var tableServices = tableServService.getTableServices(
                status,
                waiterId,
                page,
                pageSize);

        var response = new PagedApiResponse<TableServiceDetails>(
                200,
                tableServices
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TableServiceDetails>> getTableServiceDetails(@PathVariable UUID id) {
        var tableServiceDetails = tableServService.getTableServiceDetails(id);
        var response = new ApiResponse<TableServiceDetails>(
                200,
                tableServiceDetails
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{table_service_id}/close")
    public ResponseEntity<ApiResponse<ClosingTableServiceDetails>> getTableServiceClosingDetails(@PathVariable(name = "table_service_id") UUID tableServiceId) {
        var closingTableServiceDetails = tableServService.getClosingTableServiceDetails(tableServiceId);
        var response = new ApiResponse<ClosingTableServiceDetails>(
                200,
                closingTableServiceDetails
        );

        return ResponseEntity.ok(response);

    }

    @Transactional
    @PostMapping("/{table_service_id}/close")
    public ResponseEntity<NoDataApiResponse> closeTableService(
            @PathVariable(name = "table_service_id") UUID tableServiceId,
            @RequestBody CloseTableServiceRequest closeTableServiceRequest) {

        tableServService.closeTableService(tableServiceId, closeTableServiceRequest);
        var response = new NoDataApiResponse(
                200,
                "Atendimento finalizado"
        );
        return ResponseEntity.ok(response);
    }
}
