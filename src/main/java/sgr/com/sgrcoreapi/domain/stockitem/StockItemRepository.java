package sgr.com.sgrcoreapi.domain.stockitem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockItemRepository extends JpaRepository<StockItem, UUID> {
}
