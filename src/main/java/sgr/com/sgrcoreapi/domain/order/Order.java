package sgr.com.sgrcoreapi.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgr.com.sgrcoreapi.domain.saleitem.SaleItem;
import sgr.com.sgrcoreapi.domain.tableservice.TableService;
import sgr.com.sgrcoreapi.domain.user.User;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne
    private TableService tableService;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @ManyToMany
    private List<SaleItem> saleItems;

    @ManyToOne
    private User waiter;

    public Order(TableService tableService, OrderStatusEnum status, User waiter, List<SaleItem> saleItems) {
        this.tableService = tableService;
        this.status = status;
        this.waiter = waiter;
        this.saleItems = saleItems;
    }
}
