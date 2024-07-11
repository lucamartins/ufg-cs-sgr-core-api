package sgr.com.sgrcoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sgr.com.sgrcoreapi.domain.order.OrderStatusEnum;
import sgr.com.sgrcoreapi.infra.http.ApiResponse;
import sgr.com.sgrcoreapi.infra.http.HttpHelper;
import sgr.com.sgrcoreapi.infra.http.PagedApiResponse;
import sgr.com.sgrcoreapi.service.order.dto.AddOrderRequest;
import sgr.com.sgrcoreapi.service.order.dto.OrderDetails;
import sgr.com.sgrcoreapi.service.order.dto.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @Transactional
    @PostMapping
    public ResponseEntity<ApiResponse<OrderDetails>> addOrder(@RequestBody @Valid AddOrderRequest addOrderRequest) {
        var newOrder = service.addOrder(addOrderRequest);

        var resourceURI = HttpHelper.buildResourceURI(newOrder.id());

        var response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                newOrder
        );

        return ResponseEntity.created(resourceURI).body(response);
    }

    @GetMapping
    public ResponseEntity<PagedApiResponse<OrderDetails>> getOrdersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) OrderStatusEnum status
    ) {
        var orderPage = service.getOrdersPage(page, pageSize, status);

        var response = new PagedApiResponse<>(
                HttpStatus.OK.value(),
                orderPage
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDetails>> getOrder(@PathVariable UUID id) {
        var order = service.getOrder(id);

        var response = new ApiResponse<>(
                HttpStatus.OK.value(),
                order
        );

        return ResponseEntity.ok(response);
    }
}
