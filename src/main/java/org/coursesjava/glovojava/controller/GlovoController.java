package org.coursesjava.glovojava.controller;

import lombok.RequiredArgsConstructor;
import org.coursesjava.glovojava.dto.OrderDto;
import org.coursesjava.glovojava.dto.ProductDto;
import org.coursesjava.glovojava.model.OrderEntity;
import org.coursesjava.glovojava.model.ProductEntity;
import org.coursesjava.glovojava.service.OrderService;
import org.coursesjava.glovojava.service.ProductService;
import org.coursesjava.glovojava.utilit.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class GlovoController {

    private final ProductService productService;
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderService.save(OrderEntity.builder()
                .date(orderDto.getDate())
                .cost(orderDto.getCost())
                .products(orderDto.getProducts())
                .build()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        OrderEntity order = orderService.findById(id);
        return ResponseHandler.responseWithData("Found order successfully!", HttpStatus.FOUND, order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        orderService.updateById(id, OrderEntity.builder()
                .date(orderDto.getDate())
                .cost(orderDto.getCost())
                .products(orderDto.getProducts())
                .build());
        OrderEntity order = orderService.findById(id);
        return ResponseHandler.responseWithData("Found order successfully!", HttpStatus.FOUND, order);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> addProductToOrder(@PathVariable Long id, @RequestBody ProductDto product) {
        OrderEntity order = orderService.findById(id);
        product.setOrder(order);
        productService.save(ProductEntity.builder()
                .name(product.getName())
                .cost(product.getCost())
                .order(product.getOrder())
                .build());
        return ResponseHandler.responseWithData("Product add successfully!", HttpStatus.OK, order);
    }

    @DeleteMapping("/{id}/product/{name}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long id, @PathVariable String name) {
        OrderEntity order = orderService.findById(id);
        productService.deleteProductByOrderIdAndProductName(id, name);
        return ResponseHandler.responseWithData("Successfully update!", HttpStatus.OK, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}
