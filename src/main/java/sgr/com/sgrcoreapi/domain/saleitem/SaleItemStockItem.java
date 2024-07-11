package sgr.com.sgrcoreapi.domain.saleitem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgr.com.sgrcoreapi.domain.stockitem.StockItem;

import java.util.UUID;

@Entity
@Table(name = "sale_item_stock_item")
@NoArgsConstructor
@Getter
@Setter
public class SaleItemStockItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private SaleItem saleItem;

    @ManyToOne
    private StockItem stockItem;

    private Double fractionalQuantity;

    private Long wholeQuantity;

    public SaleItemStockItem(SaleItem saleItem, StockItem stockItem, Double fractionalQuantity, Long wholeQuantity) {
        this.saleItem = saleItem;
        this.stockItem = stockItem;
        this.fractionalQuantity = fractionalQuantity;
        this.wholeQuantity = wholeQuantity;
    }
}
