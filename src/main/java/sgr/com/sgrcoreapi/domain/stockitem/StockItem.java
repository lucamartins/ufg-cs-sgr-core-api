package sgr.com.sgrcoreapi.domain.stockitem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
