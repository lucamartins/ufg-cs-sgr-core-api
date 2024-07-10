package sgr.com.sgrcoreapi.domain.saleitem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgr.com.sgrcoreapi.domain.stockitem.StockItem;

import java.util.UUID;

@Entity
@Table(name = "stock_item_associations")
@NoArgsConstructor
@Getter
@Setter
public class StockItemAssociation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private SaleItem saleItem;

    @ManyToOne
    private StockItem stockItem;

    private Double fractionalQuantity;

    private Long wholeQuantity;
}
