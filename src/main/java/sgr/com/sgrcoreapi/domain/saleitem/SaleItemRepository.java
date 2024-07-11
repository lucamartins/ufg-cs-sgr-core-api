package sgr.com.sgrcoreapi.domain.saleitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SaleItemRepository extends JpaRepository<SaleItem, UUID>{
    @Query("SELECT si FROM SaleItem si " +
            "JOIN si.saleItemStockItems ssi " +
            "JOIN ssi.stockItem sti " +
            "WHERE (:isAvailable = false OR (si.isAvailable = true " +
            "AND ((sti.allowFractionalQuantity = true AND ssi.fractionalQuantity > 0 AND sti.fractionalQuantity >= ssi.fractionalQuantity) " +
            "OR (sti.allowFractionalQuantity = false AND ssi.wholeQuantity > 0 AND sti.wholeQuantity >= ssi.wholeQuantity)))) " +
            "GROUP BY si " +
            "HAVING COUNT(ssi) = (SELECT COUNT(ssi2) FROM SaleItemStockItem ssi2 WHERE ssi2.saleItem = si)")
    Page<SaleItem> findAvailableSaleItems(@Param("isAvailable") boolean isAvailable, Pageable pageable);
}
