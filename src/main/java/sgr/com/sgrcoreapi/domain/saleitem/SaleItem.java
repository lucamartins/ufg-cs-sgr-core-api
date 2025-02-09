package sgr.com.sgrcoreapi.domain.saleitem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgr.com.sgrcoreapi.domain.order.Order;
import sgr.com.sgrcoreapi.service.saleitem.dto.AddSaleItemRequest;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sale_items")
@Getter
@Setter
@NoArgsConstructor
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String name;

    private Double price;

    @Enumerated(EnumType.STRING)
    private SaleItemPriceCurrencyEnum priceCurrency;

    private Boolean isAvailable;

    @OneToMany(mappedBy = "saleItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItemStockItem> saleItemStockItems;

    @ManyToMany(mappedBy = "saleItems")
    private List<Order> orders;

    public SaleItem(AddSaleItemRequest addSaleItemRequest) {
        this.name = addSaleItemRequest.name();
        this.price = addSaleItemRequest.price();
        this.priceCurrency = addSaleItemRequest.priceCurrency();
        this.isAvailable = addSaleItemRequest.isSaleAvailable();
        this.saleItemStockItems = null;
    }

    public boolean getIsSaleAvailable() {
        if (!this.isAvailable) {
            return false;
        }

        return this.saleItemStockItems.stream().allMatch(saleItemStockItem -> {
            var stockItem = saleItemStockItem.getStockItem();
            var fractionalQuantity = saleItemStockItem.getFractionalQuantity();
            var wholeQuantity = saleItemStockItem.getWholeQuantity();

            if (stockItem.getAllowFractionalQuantity()) {
                return stockItem.getFractionalQuantity() >= fractionalQuantity;
            }

            return stockItem.getWholeQuantity() >= wholeQuantity;
        });
    }

    public boolean canBeDeleted() {
        return this.orders.isEmpty();
    }
}
