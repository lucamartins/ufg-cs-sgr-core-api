package sgr.com.sgrcoreapi.service.stockitem;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sgr.com.sgrcoreapi.domain.stockitem.StockItem;
import sgr.com.sgrcoreapi.domain.stockitem.StockItemRepository;
import sgr.com.sgrcoreapi.infra.exception.custom.BadRequestException;
import sgr.com.sgrcoreapi.infra.exception.custom.NotFoundException;
import sgr.com.sgrcoreapi.service.stockitem.dto.AddStockItemRequest;
import sgr.com.sgrcoreapi.service.stockitem.dto.StockItemDetails;
import sgr.com.sgrcoreapi.service.stockitem.dto.StockMovementRequest;
import sgr.com.sgrcoreapi.service.stockitem.dto.UpdateStockItemRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockItemService {

    private final StockItemRepository stockItemRepository;

    public StockItemDetails createStockItem(AddStockItemRequest addStockItemRequest) {
        StockItem newStockItem = new StockItem(addStockItemRequest);

        stockItemRepository.save(newStockItem);

        return new StockItemDetails(newStockItem);
    }

    public void deleteStockItem(UUID stockItemId) {
        var stockItem = stockItemRepository
                .findById(stockItemId)
                .orElseThrow(NotFoundException::new);

        if (!stockItem.canBeDeleted()) {
            throw new BadRequestException("Stock item cannot be deleted");
        }

        stockItemRepository.deleteById(stockItemId);
    }

    public StockItemDetails updateStockItem(UUID stockItemId, UpdateStockItemRequest updateStockItemRequest) {
        var curStockItem = stockItemRepository
                .findById(stockItemId)
                .orElseThrow(NotFoundException::new);

        curStockItem.setName(updateStockItemRequest.name());

        stockItemRepository.save(curStockItem);

        return new StockItemDetails(curStockItem);
    }

    public StockItemDetails getStockItem(UUID stockItemId) {
        var stockItem = stockItemRepository
                .findById(stockItemId)
                .orElseThrow(NotFoundException::new);

        return new StockItemDetails(stockItem);
    }

    public Page<StockItemDetails> getStockItemPage(
            int page,
            int pageSize,
            boolean isEmpty
    ) {
        Pageable pageableConfig = Pageable.ofSize(pageSize).withPage(page);

        var stockItemsPage = stockItemRepository.findAllByIsEmpty(isEmpty, pageableConfig);

        return stockItemsPage.map(StockItemDetails::new);
    }

    public StockItemDetails createStockMovement(UUID stockItemId, StockMovementRequest stockMovementRequest) {
        var stockItem = stockItemRepository
                .findById(stockItemId)
                .orElseThrow(() -> new BadRequestException("Stock item not found"));

        var isFractionalQuantityRequired = stockItem.getAllowFractionalQuantity();

        if (isFractionalQuantityRequired && stockMovementRequest.fractionalQuantity() == null) {
            throw new BadRequestException("Fractional quantity must be provided when stock item allows fractional quantity");
        }

        if (!isFractionalQuantityRequired && stockMovementRequest.wholeQuantity() == null) {
            throw new BadRequestException("Whole quantity must be provided when stock item does not allow fractional quantity");
        }

        if (!stockItem.canProcessMovement(stockMovementRequest)) {
            throw new BadRequestException("Stock item does not have enough quantity to process the movement");
        }

        stockItem.processMovement(stockMovementRequest);

        stockItemRepository.save(stockItem);

        return new StockItemDetails(stockItem);
    }
}
