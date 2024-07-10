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
import sgr.com.sgrcoreapi.service.stockitem.dto.UpdateStockItemRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockItemService {

    private final StockItemRepository stockItemRepository;

    public void createStockItem(AddStockItemRequest addStockItemRequest) {
        if (addStockItemRequest.allowFractionalQuantity() && addStockItemRequest.fractionalQuantity() == null) {
            throw new BadRequestException("Fractional quantity must be provided when allowing fractional quantity");
        }

        if (!addStockItemRequest.allowFractionalQuantity() && addStockItemRequest.wholeQuantity() == null) {
            throw new BadRequestException("Whole quantity must be provided when not allowing fractional quantity");
        }

        StockItem newStockItem = new StockItem(addStockItemRequest);

        stockItemRepository.save(newStockItem);
    }

    public void deleteStockItem(UUID stockItemId) {
        // TODO: validate if stock item can be deleted
        stockItemRepository.deleteById(stockItemId);
    }

    public void updateStockItem(UUID stockItemId, UpdateStockItemRequest updateStockItemRequest) {
        var curStockItem = stockItemRepository
                .findById(stockItemId)
                .orElseThrow(NotFoundException::new);

        curStockItem.setName(updateStockItemRequest.name());

        stockItemRepository.save(curStockItem);
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
}
