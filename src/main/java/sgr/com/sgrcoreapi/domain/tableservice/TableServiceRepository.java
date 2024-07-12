package sgr.com.sgrcoreapi.domain.tableservice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sgr.com.sgrcoreapi.domain.table.CustomerTable;

public interface TableServiceRepository extends JpaRepository<TableService, UUID>{
    @Query("SELECT t FROM TableService t WHERE "
            + "(:status IS NULL OR t.status = :status) AND "
            + "(:waiterId IS NULL OR t.waiter.id = :waiterId)")
    Page<TableService> findByStatusAndWaiter(@Param("status") TableServiceStatus status,
                                             @Param("waiterId") UUID waiterId,
                                             Pageable pageable);

    @Query("SELECT t FROM TableService t WHERE "
            + "(CAST(:waiterId AS uuid) IS NULL OR t.waiter.id = :waiterId) AND "
            + "(CAST(:startDate AS timestamp) IS NULL OR t.startedAt >= :startDate) AND "
            + "(CAST(:endDate AS timestamp) IS NULL OR t.startedAt <= :endDate)")
    List<TableService> findByDateRangeAndWaiter(@Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate,
                                                @Param("waiterId") UUID waiterId);

    boolean existsByCustomerTableAndStatus(CustomerTable customerTable, TableServiceStatus status);

}
