package org.coursesjava.glovojava.controller;

import lombok.AllArgsConstructor;
import org.coursesjava.glovojava.model.OrderEntity;
import org.coursesjava.glovojava.model.ProductEntity;
import org.coursesjava.glovojava.repository.ProductRepository;
import org.coursesjava.glovojava.service.OrderService;
import org.coursesjava.glovojava.utilit.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class GlovoController {

    private ProductRepository productRepository;
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity order) {
        return new ResponseEntity<>(orderService.save(order), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        ResponseEntity<OrderEntity> response = orderService.findById(id);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) return ResponseHandler.response("Order not found!", HttpStatus.NOT_FOUND);
        return ResponseHandler.responseWithData("Found order successfully!", HttpStatus.FOUND, response.getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Long id, @RequestBody OrderEntity order) {
        orderService.updateById(id, order);
        return orderService.findById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> addProductToOrder(@PathVariable Long id, @RequestBody ProductEntity product) {
        ResponseEntity<OrderEntity> response = orderService.findById(id);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) return ResponseHandler.response("Order not found!", HttpStatus.NOT_FOUND);
        product.setOrder(response.getBody());
        productRepository.save(product);
        return ResponseHandler.responseWithData("Product add successfully!", HttpStatus.OK, response.getBody());
    }

    @DeleteMapping("/{id}/product/{name}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long id, @PathVariable String name) {
        ResponseEntity<OrderEntity> response = orderService.findById(id);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) return ResponseHandler.response("Order not found!", HttpStatus.NOT_FOUND);
        productRepository.deleteProductByIdAndName(id, name);
        return ResponseHandler.responseWithData("Successfully update!", HttpStatus.OK, response.getBody());
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}
