package sgr.com.sgrcoreapi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/stock-tems")
public class StockItemController {

    @PostMapping
    public void createStockItem() {
    }

    @GetMapping
    public void getStockItems() {
    }

    @GetMapping("/{id}")
    public void getStockItem(@PathVariable UUID id) {
    }

    @PatchMapping("/{id}")
    public void updateStockItem(@PathVariable UUID id) {
    }

    @DeleteMapping("/{id}")
    public void deleteStockItem(@PathVariable UUID id) {
    }

    @PostMapping("/{id}/stock-movements")
    public void createStockMovement(@PathVariable UUID id) {
    }
}
