package sgr.com.sgrcoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sgr.com.sgrcoreapi.infra.http.ApiResponse;
import sgr.com.sgrcoreapi.infra.http.HttpHelper;
import sgr.com.sgrcoreapi.infra.http.NoDataApiResponse;
import sgr.com.sgrcoreapi.infra.http.PagedApiResponse;
import sgr.com.sgrcoreapi.service.saleitem.SaleItemService;
import sgr.com.sgrcoreapi.service.saleitem.dto.AddSaleItemRequest;
import sgr.com.sgrcoreapi.service.saleitem.dto.SaleItemDetails;
import sgr.com.sgrcoreapi.service.saleitem.dto.UpdateSaleItemRequest;

import java.util.UUID;

@RestController
@RequestMapping("/sale-items")
@RequiredArgsConstructor
public class SaleItemController {

    private final SaleItemService service;

    @Transactional
    @PostMapping
    public ResponseEntity<ApiResponse<SaleItemDetails>> createSaleItem(@RequestBody @Valid AddSaleItemRequest addSaleItemRequest) {
        var saleItem = service.createSaleItem(addSaleItemRequest);

        var resourceURI = HttpHelper.buildResourceURI(saleItem.id());

        var response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                saleItem
        );

        return ResponseEntity.created(resourceURI).body(response);
    }

    @GetMapping
    public ResponseEntity<PagedApiResponse<SaleItemDetails>> getSaleItemsList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "false") boolean isAvailable
    ) {
        var saleItems = service.getSaleItemsPage(page, pageSize, isAvailable);

        var response = new PagedApiResponse<>(
                HttpStatus.OK.value(),
                saleItems
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleItemDetails>> getSaleItem(@PathVariable UUID id) {
        var saleItem = service.getSaleItem(id);

        var response = new ApiResponse<>(
                HttpStatus.OK.value(),
                saleItem
        );

        return ResponseEntity.ok(response);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<SaleItemDetails>> updateSaleItem(@PathVariable UUID id,
                                @RequestBody @Valid UpdateSaleItemRequest updateSaleItemRequest) {
        var updatedSaleItem = service.updateSaleItem(id, updateSaleItemRequest);

        var response = new ApiResponse<>(
                HttpStatus.OK.value(),
                updatedSaleItem
        );

        return ResponseEntity.ok(response);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<NoDataApiResponse> deleteSaleItem(@PathVariable UUID id) {
        service.deleteSaleItem(id);

        var response = new NoDataApiResponse(
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(response);
    }
}
