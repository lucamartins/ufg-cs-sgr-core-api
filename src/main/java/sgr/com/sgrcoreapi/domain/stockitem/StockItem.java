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

    @Enumerated(EnumType.STRING)
    private StockItemMeasurementUnitEnum measurementUnit;

    private Boolean allowFractionalQuantity;

    private Double fractionalQuantity;

    private Long wholeQuantity;

    public StockItem(AddStockItemRequest addStockItemRequest) {
        var isFractional = addStockItemRequest.allowFractionalQuantity();

        this.name = addStockItemRequest.name();
        this.measurementUnit = addStockItemRequest.measurementUnit();
        this.allowFractionalQuantity = isFractional;

        if (isFractional) {
            this.wholeQuantity = null;
            this.fractionalQuantity = addStockItemRequest.fractionalQuantity() != null
                    ? addStockItemRequest.fractionalQuantity()
                    : 0.0;
            return;
        }

        this.fractionalQuantity = null;
        this.wholeQuantity = addStockItemRequest.wholeQuantity() != null
                ? addStockItemRequest.wholeQuantity()
                : 0L;
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
