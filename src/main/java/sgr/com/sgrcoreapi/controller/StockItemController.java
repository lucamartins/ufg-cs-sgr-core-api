package sgr.com.sgrcoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sgr.com.sgrcoreapi.infra.http.ApiResponse;
import sgr.com.sgrcoreapi.infra.http.HttpHelper;
import sgr.com.sgrcoreapi.infra.http.NoDataApiResponse;
import sgr.com.sgrcoreapi.infra.http.PagedApiResponse;
import sgr.com.sgrcoreapi.service.stockitem.StockItemService;
import sgr.com.sgrcoreapi.service.stockitem.dto.AddStockItemRequest;
import sgr.com.sgrcoreapi.service.stockitem.dto.StockItemDetails;
import sgr.com.sgrcoreapi.service.stockitem.dto.UpdateStockItemRequest;

import java.util.UUID;

@RestController
@RequestMapping("/stock-items")
@RequiredArgsConstructor
public class StockItemController {

    private final StockItemService service;

    @PostMapping
    public ResponseEntity<NoDataApiResponse> createStockItem(@RequestBody @Valid AddStockItemRequest addStockItemRequest) {
        service.createStockItem(addStockItemRequest);

        var resourceURI = HttpHelper.getCreatedResourceURI(UUID.randomUUID());
        var response = new NoDataApiResponse(HttpStatus.CREATED.value());

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

    @PatchMapping("/{id}")
    public ResponseEntity<NoDataApiResponse> updateStockItem(@PathVariable UUID id,
                                                             @RequestBody @Valid UpdateStockItemRequest updateStockItemRequest) {
        service.updateStockItem(id, updateStockItemRequest);

        var response = new NoDataApiResponse(HttpStatus.NO_CONTENT.value());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NoDataApiResponse> deleteStockItem(@PathVariable UUID id) {
        service.deleteStockItem(id);

        var response = new NoDataApiResponse(HttpStatus.NO_CONTENT.value());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/stock-movements")
    public void createStockMovement(@PathVariable UUID id) {
        // TODO: implement
    }
}
