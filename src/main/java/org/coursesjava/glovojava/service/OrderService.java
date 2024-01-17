package org.coursesjava.glovojava.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.coursesjava.glovojava.model.OrderEntity;
import org.coursesjava.glovojava.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderEntity save(OrderEntity order) {
        return orderRepository.save(order);
    }

    public Optional<OrderEntity> findById(Long id) {
        return orderRepository.findById(id);
    }

    public void updateById(Long id, OrderEntity order) {
        orderRepository.updateById(id, order);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
