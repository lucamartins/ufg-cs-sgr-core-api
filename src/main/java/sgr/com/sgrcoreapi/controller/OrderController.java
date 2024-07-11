package sgr.com.sgrcoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sgr.com.sgrcoreapi.service.order.dto.AddOrderRequest;
import sgr.com.sgrcoreapi.service.order.dto.OrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public void addOrder(@RequestBody @Valid AddOrderRequest addOrderRequest) {
        service.addOrder(addOrderRequest);
    }
}
