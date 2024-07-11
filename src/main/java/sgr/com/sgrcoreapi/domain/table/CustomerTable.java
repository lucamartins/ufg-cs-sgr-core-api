package sgr.com.sgrcoreapi.domain.table;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import sgr.com.sgrcoreapi.service.table.dto.AddTableRequest;

@Entity
@Table(name = "tables")
@Data
@NoArgsConstructor
public class CustomerTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public CustomerTable(AddTableRequest addTableRequest) {
        this.isAvailable = addTableRequest.isAvailable();
    }


    public void changeAvailability() {
        this.isAvailable = !this.isAvailable;
    }
}
