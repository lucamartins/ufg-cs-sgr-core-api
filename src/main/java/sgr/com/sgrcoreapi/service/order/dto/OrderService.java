package sgr.com.sgrcoreapi.service.order.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sgr.com.sgrcoreapi.domain.order.Order;
import sgr.com.sgrcoreapi.domain.order.OrderRepository;
import sgr.com.sgrcoreapi.domain.order.OrderStatusEnum;
import sgr.com.sgrcoreapi.domain.saleitem.SaleItemRepository;
import sgr.com.sgrcoreapi.domain.tableservice.TableServiceRepository;
import sgr.com.sgrcoreapi.domain.tableservice.TableServiceStatus;
import sgr.com.sgrcoreapi.domain.user.UserRepository;
import sgr.com.sgrcoreapi.infra.exception.custom.BadRequestException;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TableServiceRepository tableServiceRepository;
    private final UserRepository userRepository;
    private final SaleItemRepository saleItemRepository;

    public OrderDetails addOrder(AddOrderRequest addOrderRequest) {
        // GET TABLE SERVICE AND VALIDATE IT
        var tableService = tableServiceRepository
                .findById(addOrderRequest.tableServiceId())
                .orElseThrow(() -> new BadRequestException(
                        "Table service not found: " + addOrderRequest.tableServiceId())
                );

        if (tableService.getStatus() != TableServiceStatus.IN_PROGRESS) {
            throw new BadRequestException("Table service must be in progress to register an order.");
        }

        // GET WAITER AND VALIDATE IT
        var waiter = userRepository
                .findById(addOrderRequest.waiterId())
                .orElseThrow(() -> new BadRequestException(
                        "Waiter not found: " + addOrderRequest.waiterId())
                );

        // GET SALE ITEMS AND VALIDATE THEM
        var saleItems = saleItemRepository
                .findAllById(addOrderRequest.saleItemsIds());

        var saleItemsValidationErrors = new ArrayList<String>();

        addOrderRequest.saleItemsIds().forEach(saleItemId -> {
            var matchingSaleItem = saleItems
                    .stream()
                    .filter(saleItem -> saleItem.getId().equals(saleItemId))
                    .findFirst();

            if (matchingSaleItem.isEmpty()) {
                saleItemsValidationErrors.add("Sale item not found: " + saleItemId);

            }

            if (matchingSaleItem.isPresent() && !matchingSaleItem.get().getIsSaleAvailable()) {
                saleItemsValidationErrors.add("Sale item not available: " + saleItemId);
            }
        });

        if (!saleItemsValidationErrors.isEmpty()) {
            throw new BadRequestException(saleItemsValidationErrors);
        }

        // PERSIST DATA
        var newOrder = new Order(
                tableService,
                OrderStatusEnum.PENDING,
                waiter,
                saleItems
        );

        orderRepository.save(newOrder);

        return new OrderDetails(newOrder);
    }
}
