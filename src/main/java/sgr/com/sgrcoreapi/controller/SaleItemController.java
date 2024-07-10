package sgr.com.sgrcoreapi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sale-items")
public class SaleItemController {

    @PostMapping
    public void createSaleItem() {}

    @GetMapping
    public void getSaleItemsList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "false") boolean isAvailable
    ) {

    }

    @GetMapping("/{id}")
    public void getSaleItem(@PathVariable UUID id) {

    }

    @PatchMapping("/{id}")
    public void updateSaleItem(@PathVariable UUID id) {

    }

    @DeleteMapping("/{id}")
    public void deleteSaleItem(@PathVariable UUID id) {

    }
}
