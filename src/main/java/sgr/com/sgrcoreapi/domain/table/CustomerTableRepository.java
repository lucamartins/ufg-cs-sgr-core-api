package sgr.com.sgrcoreapi.domain.table;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerTableRepository extends JpaRepository<CustomerTable, UUID> {
    Page<CustomerTable> findByIsAvailable(boolean isAvailable, Pageable pageable);
    Page<CustomerTable> findByIsDeletedFalse(Pageable pageable);
    Optional<CustomerTable> findByIdAndIsDeletedFalse(UUID id);

    @Query("SELECT t FROM CustomerTable t WHERE t.isAvailable = :isAvailable AND t.isDeleted = false")
    Page<CustomerTable> findByIsAvailableAndIsDeletedFalse(@Param("isAvailable") boolean isAvailable, Pageable pageable);
}
