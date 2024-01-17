package org.coursesjava.glovojava.controller;

import lombok.RequiredArgsConstructor;
import org.coursesjava.glovojava.model.OrderEntity;
import org.coursesjava.glovojava.model.ProductEntity;
import org.coursesjava.glovojava.repository.ProductRepository;
import org.coursesjava.glovojava.service.OrderService;
import org.coursesjava.glovojava.utilit.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class GlovoController {

    private final ProductRepository productRepository;
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity order) {
        return new ResponseEntity<>(orderService.save(order), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        return orderService.findById(id).map(orderEntity -> ResponseHandler.responseWithData("Found order successfully!", HttpStatus.FOUND, orderEntity))
                .orElseGet(() -> ResponseHandler.response("Order not found!", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateOrder(@PathVariable Long id, @RequestBody OrderEntity order) {
        orderService.updateById(id, order);
        return orderService.findById(id).map(orderEntity -> ResponseHandler.responseWithData("Found order successfully!", HttpStatus.FOUND, orderEntity))
                .orElseGet(() -> ResponseHandler.response("Order not found!", HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> addProductToOrder(@PathVariable Long id, @RequestBody ProductEntity product) {
        Optional<OrderEntity> order = orderService.findById(id);
        if (order.isEmpty()) return ResponseHandler.response("Order not found!", HttpStatus.NOT_FOUND);
        product.setOrder(order.get());
        productRepository.save(product);
        return ResponseHandler.responseWithData("Product add successfully!", HttpStatus.OK, order.get());
    }

    @DeleteMapping("/{id}/product/{name}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long id, @PathVariable String name) {
        Optional<OrderEntity> order = orderService.findById(id);
        if (order.isEmpty()) return ResponseHandler.response("Order not found!", HttpStatus.NOT_FOUND);
        productRepository.deleteProductByIdAndName(id, name);
        return ResponseHandler.responseWithData("Successfully update!", HttpStatus.OK, order.get());
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}
