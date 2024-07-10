package sgr.com.sgrcoreapi.domain.stockitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface StockItemRepository extends JpaRepository<StockItem, UUID> {
    @Query("SELECT s FROM StockItem s WHERE " +
            "(:isEmpty = false OR " +
            "(s.allowFractionalQuantity = true AND s.fractionalQuantity = 0.0) OR " +
            "(s.allowFractionalQuantity = false AND s.wholeQuantity = 0))")
    Page<StockItem> findAllByIsEmpty(boolean isEmpty, Pageable pageable);
}
