package sgr.com.sgrcoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sgr.com.sgrcoreapi.infra.http.HttpHelper;
import sgr.com.sgrcoreapi.infra.http.NoDataApiResponse;
import sgr.com.sgrcoreapi.service.saleitem.SaleItemService;
import sgr.com.sgrcoreapi.service.saleitem.dto.AddSaleItemRequest;

import java.util.UUID;

@RestController
@RequestMapping("/sale-items")
@RequiredArgsConstructor
public class SaleItemController {

    private final SaleItemService service;

    @PostMapping
    public ResponseEntity<NoDataApiResponse> createSaleItem(@RequestBody @Valid AddSaleItemRequest addSaleItemRequest) {
        var saleItemId = service.createSaleItem(addSaleItemRequest);

        var resourceURI = HttpHelper.getCreatedResourceURI(saleItemId);

        var response = new NoDataApiResponse(
                HttpStatus.CREATED.value()
        );

        return ResponseEntity.created(resourceURI).body(response);
    }

    @GetMapping
    public void getSaleItemsList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "false") boolean isAvailable
    ) {

    }

    @GetMapping("/{id}")
    public void getSaleItem(@PathVariable UUID id) {

    }

    @PatchMapping("/{id}")
    public void updateSaleItem(@PathVariable UUID id) {

    }

    @DeleteMapping("/{id}")
    public void deleteSaleItem(@PathVariable UUID id) {

    }
}
