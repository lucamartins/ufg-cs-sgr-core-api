package sgr.com.sgrcoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sgr.com.sgrcoreapi.infra.http.ApiResponse;
import sgr.com.sgrcoreapi.infra.http.HttpHelper;
import sgr.com.sgrcoreapi.service.order.dto.AddOrderRequest;
import sgr.com.sgrcoreapi.service.order.dto.OrderDetails;
import sgr.com.sgrcoreapi.service.order.dto.OrderService;

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
}
