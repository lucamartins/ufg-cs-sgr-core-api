package sgr.com.sgrcoreapi.domain.saleitem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(mappedBy = "saleItem")
    private List<StockItemAssociation> stockItemAssociations;
}
