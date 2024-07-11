package sgr.com.sgrcoreapi.domain.saleitem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleItemRepository extends JpaRepository<SaleItem, UUID>{
}
