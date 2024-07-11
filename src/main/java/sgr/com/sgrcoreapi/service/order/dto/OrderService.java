package sgr.com.sgrcoreapi.service.order.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sgr.com.sgrcoreapi.domain.order.OrderRepository;
import sgr.com.sgrcoreapi.domain.tableservice.TableServiceRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TableServiceRepository tableServiceRepository;

    public void addOrder(AddOrderRequest addOrderRequest) {

    }
}
