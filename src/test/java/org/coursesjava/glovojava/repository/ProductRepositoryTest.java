package org.coursesjava.glovojava.repository;

import org.coursesjava.glovojava.model.OrderEntity;
import org.coursesjava.glovojava.model.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void deleteProductByIdAndName() {
        List<ProductEntity> products = new ArrayList<>();
        OrderEntity order = new OrderEntity();
        order.setId(2);
        order.setDate(LocalDate.of(2022, 5, 6));
        order.setCost(170);
        order.setProducts(products);

        productRepository.deleteProductByOrderIdAndProductName(2L, "Beer");

        Optional<OrderEntity> foundOrder = orderRepository.findById(2L);
        assertTrue(foundOrder.isPresent());
        assertEquals(order, foundOrder.get());
    }
}
