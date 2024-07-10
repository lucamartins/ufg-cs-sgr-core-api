package sgr.com.sgrcoreapi.domain.stockitem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgr.com.sgrcoreapi.service.stockitem.dto.AddStockItemRequest;
import sgr.com.sgrcoreapi.service.stockitem.dto.StockMovementRequest;
import sgr.com.sgrcoreapi.service.stockitem.dto.StockMovementTypeEnum;

import java.util.UUID;

@Entity
@Table(name = "stock_items")
@Getter
@Setter
@NoArgsConstructor
public class StockItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String name;

    private Boolean allowFractionalQuantity;

    private Double fractionalQuantity;

    private Long wholeQuantity;

    public StockItem(AddStockItemRequest addStockItemRequest) {
        this.name = addStockItemRequest.name();
        this.allowFractionalQuantity = addStockItemRequest.allowFractionalQuantity();
        this.fractionalQuantity = addStockItemRequest.fractionalQuantity();
        this.wholeQuantity = addStockItemRequest.wholeQuantity();
    }

    public boolean canProcessMovement(StockMovementRequest stockMovementRequest) {
        if (stockMovementRequest.type() == StockMovementTypeEnum.IN) {
            return true;
        }

        if (allowFractionalQuantity) {
            return fractionalQuantity >= stockMovementRequest.fractionalQuantity();
        }

        return wholeQuantity >= stockMovementRequest.wholeQuantity();
    }

    public void processMovement(StockMovementRequest stockMovementRequest) {
        if (stockMovementRequest.type() == StockMovementTypeEnum.IN) {
            if (allowFractionalQuantity) {
                fractionalQuantity += stockMovementRequest.fractionalQuantity();
                return;
            }

            wholeQuantity += stockMovementRequest.wholeQuantity();
            return;
        }

        if (allowFractionalQuantity) {
            fractionalQuantity -= stockMovementRequest.fractionalQuantity();
            return;
        }

        wholeQuantity -= stockMovementRequest.wholeQuantity();
    }
}
