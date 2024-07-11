package sgr.com.sgrcoreapi.controller;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sgr.com.sgrcoreapi.domain.tableservice.TableService;
import sgr.com.sgrcoreapi.domain.tableservice.TableServiceStatus;
import sgr.com.sgrcoreapi.infra.http.NoDataApiResponse;
import sgr.com.sgrcoreapi.infra.http.PagedApiResponse;
import sgr.com.sgrcoreapi.service.tableService.TableServiceService;
import sgr.com.sgrcoreapi.service.tableService.dto.AddTableServiceRequest;
import sgr.com.sgrcoreapi.service.tableService.dto.TableServiceDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/table-service")
public class TableServiceController {

    private final TableServiceService tableServService;

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
}
