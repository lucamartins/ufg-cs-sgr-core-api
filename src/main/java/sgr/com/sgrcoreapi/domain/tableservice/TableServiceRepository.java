package sgr.com.sgrcoreapi.domain.tableservice;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TableServiceRepository extends JpaRepository<TableService, UUID>{
    @Query("SELECT t FROM TableService t WHERE "
            + "(:status IS NULL OR t.status = :status) AND "
            + "(:waiterId IS NULL OR t.waiter.id = :waiterId)")
    Page<TableService> findByStatusAndWaiter(@Param("status") TableServiceStatus status,
                                             @Param("waiterId") UUID waiterId,
                                             Pageable pageable);
}
