package sgr.com.sgrcoreapi.domain.table;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TableRepository extends JpaRepository<Table, UUID> {
    Page<Table> findByIsAvailable(boolean isAvailable, Pageable pageable);
    Page<Table> findByIsDeletedFalse(Pageable pageable);
    Optional<Table> findByIdAndIsDeletedFalse(UUID id);

    @Query("SELECT t FROM Table t WHERE t.isAvailable = :isAvailable AND t.isDeleted = false")
    Page<Table> findByIsAvailableAndIsDeletedFalse(@Param("isAvailable") boolean isAvailable, Pageable pageable);
}
