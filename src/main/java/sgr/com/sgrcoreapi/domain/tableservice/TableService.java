package sgr.com.sgrcoreapi.domain.tableservice;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import sgr.com.sgrcoreapi.domain.order.Order;
import sgr.com.sgrcoreapi.domain.table.CustomerTable;
import sgr.com.sgrcoreapi.domain.user.User;

@Entity
@Table(name= "table_service")
@NoArgsConstructor
@Data
public class TableService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TableServiceStatus status = TableServiceStatus.IN_PROGRESS;

    private Double paidAmount = (double) 0;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "table_id")
    private CustomerTable customerTable;

    @ManyToOne
    @JoinColumn(name = "waiter_id")
    private User waiter;

    @OneToMany(mappedBy = "tableService", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

}
