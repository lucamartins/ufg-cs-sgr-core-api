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
import sgr.com.sgrcoreapi.service.stockitem.StockItemService;
import sgr.com.sgrcoreapi.service.stockitem.dto.AddStockItemRequest;
import sgr.com.sgrcoreapi.service.stockitem.dto.StockItemDetails;
import sgr.com.sgrcoreapi.service.stockitem.dto.StockMovementRequest;
import sgr.com.sgrcoreapi.service.stockitem.dto.UpdateStockItemRequest;

import java.util.UUID;

@RestController
@RequestMapping("/stock-items")
@RequiredArgsConstructor
public class StockItemController {

    private final StockItemService service;

    @Transactional
    @PostMapping
    public ResponseEntity<ApiResponse<StockItemDetails>> createStockItem(@RequestBody @Valid AddStockItemRequest addStockItemRequest) {
        var createdStockItem = service.createStockItem(addStockItemRequest);

        var resourceURI = HttpHelper.buildResourceURI(createdStockItem.id());

        var response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                createdStockItem
        );

        return ResponseEntity.created(resourceURI).body(response);
    }

    @GetMapping
    public ResponseEntity<PagedApiResponse<StockItemDetails>> getStockItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "false") boolean isEmpty
    ) {
        var stockItemsPage = service.getStockItemPage(page, pageSize, isEmpty);

        var response = new PagedApiResponse<>(
                HttpStatus.OK.value(),
                stockItemsPage
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StockItemDetails>> getStockItem(@PathVariable UUID id) {
        var stockItem = service.getStockItem(id);

        var response = new ApiResponse<>(
                HttpStatus.OK.value(),
                stockItem
        );

        return ResponseEntity.ok(response);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<StockItemDetails>> updateStockItem(@PathVariable UUID id,
                                                             @RequestBody @Valid UpdateStockItemRequest updateStockItemRequest) {
        var updatedStockItem = service.updateStockItem(id, updateStockItemRequest);

        var response = new ApiResponse<>(HttpStatus.OK.value(), updatedStockItem);

        return ResponseEntity.ok(response);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<NoDataApiResponse> deleteStockItem(@PathVariable UUID id) {
        service.deleteStockItem(id);

        var response = new NoDataApiResponse(HttpStatus.NO_CONTENT.value());

        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping("/{id}/movement")
    public ResponseEntity<ApiResponse<StockItemDetails>> createStockMovement(@PathVariable UUID id,
                                  @RequestBody @Valid StockMovementRequest stockMovementRequest) {
        var newStockItem = service.createStockMovement(id, stockMovementRequest);

        var response = new ApiResponse<>(
                HttpStatus.OK.value(),
                newStockItem
        );

        return ResponseEntity.ok(response);
    }
}
