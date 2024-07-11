package sgr.com.sgrcoreapi.domain.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID>{

    Page<Order> findAllByStatus(OrderStatusEnum status, Pageable pageable);
}
