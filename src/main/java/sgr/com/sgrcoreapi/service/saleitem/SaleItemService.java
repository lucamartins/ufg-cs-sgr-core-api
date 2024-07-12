package sgr.com.sgrcoreapi.service.saleitem;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sgr.com.sgrcoreapi.domain.saleitem.SaleItem;
import sgr.com.sgrcoreapi.domain.saleitem.SaleItemRepository;
import sgr.com.sgrcoreapi.domain.saleitem.SaleItemStockItem;
import sgr.com.sgrcoreapi.domain.stockitem.StockItem;
import sgr.com.sgrcoreapi.domain.stockitem.StockItemRepository;
import sgr.com.sgrcoreapi.infra.exception.custom.BadRequestException;
import sgr.com.sgrcoreapi.infra.exception.custom.NotFoundException;
import sgr.com.sgrcoreapi.service.saleitem.dto.AddSaleItemRequest;
import sgr.com.sgrcoreapi.service.saleitem.dto.AddSaleItemRequestStockItem;
import sgr.com.sgrcoreapi.service.saleitem.dto.SaleItemDetails;
import sgr.com.sgrcoreapi.service.saleitem.dto.UpdateSaleItemRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleItemService {

    private final SaleItemRepository saleItemRepository;
    private final StockItemRepository stockItemRepository;

    private static Boolean getFractionalQuantityRequired(AddSaleItemRequestStockItem stockItem, StockItem stockItemDoc) {
        var isFractionalQuantityRequired = stockItemDoc.getAllowFractionalQuantity();

        if (isFractionalQuantityRequired && stockItem.fractionalQuantity() == null) {
            throw new BadRequestException("Fractional quantity is required for stock item: " + stockItem.stockItemId());
        }

        if (!isFractionalQuantityRequired && stockItem.wholeQuantity() == null) {
            throw new BadRequestException("Whole quantity is required for stock item: " + stockItem.stockItemId());
        }

        return isFractionalQuantityRequired;
    }

    public SaleItemDetails createSaleItem(AddSaleItemRequest addSaleItemRequest) {
        SaleItem newSaleItem = new SaleItem(addSaleItemRequest);
        List<SaleItemStockItem> saleItemStockItems = new ArrayList<>();

        addSaleItemRequest.stockItems().forEach(stockItem -> {
            var stockItemDoc =  stockItemRepository
                    .findById(stockItem.stockItemId())
                    .orElseThrow(() -> new BadRequestException("Stock item not found: " + stockItem.stockItemId()));

            var isFractionalQuantityRequired = getFractionalQuantityRequired(stockItem, stockItemDoc);

            saleItemStockItems.add(new SaleItemStockItem(
                    newSaleItem,
                    stockItemDoc,
                    isFractionalQuantityRequired ? stockItem.fractionalQuantity() : null,
                    !isFractionalQuantityRequired ? stockItem.wholeQuantity() : null
            ));
        });

        newSaleItem.setSaleItemStockItems(saleItemStockItems);
        var saleItem = saleItemRepository.save(newSaleItem);
        return new SaleItemDetails(saleItem);
    }

    public SaleItemDetails getSaleItem(UUID saleItemId) {
        var saleItem = saleItemRepository
                .findById(saleItemId)
                .orElseThrow(NotFoundException::new);

        return new SaleItemDetails(saleItem);
    }

    public Page<SaleItemDetails> getSaleItemsPage(int page, int pageSize, boolean isAvailable) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(page);

        Page<SaleItem> saleItemsPage = saleItemRepository.findAvailableSaleItems(isAvailable, pageable);

        return saleItemsPage.map(SaleItemDetails::new);
    }

    public SaleItemDetails updateSaleItem(UUID saleItemId, UpdateSaleItemRequest updateSaleItemRequest) {
        var saleItem = saleItemRepository
                .findById(saleItemId)
                .orElseThrow(NotFoundException::new);

        if (updateSaleItemRequest.name() != null) {
            saleItem.setName(updateSaleItemRequest.name());
        }

        if (updateSaleItemRequest.isAvailable() != null) {
            saleItem.setIsAvailable(updateSaleItemRequest.isAvailable());
        }

        saleItemRepository.save(saleItem);

        return new SaleItemDetails(saleItem);
    }

    public void deleteSaleItem(UUID saleItemId) {
        var saleItem = saleItemRepository
                .findById(saleItemId)
                .orElseThrow(NotFoundException::new);

        if (!saleItem.canBeDeleted()) {
            throw new BadRequestException("Sale item cannot be deleted. There are orders associated with it.");
        }

        System.out.println();
        saleItemRepository.delete(saleItem);
    }
}
