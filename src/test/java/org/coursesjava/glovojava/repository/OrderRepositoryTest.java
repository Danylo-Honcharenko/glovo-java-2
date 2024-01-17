package org.coursesjava.glovojava.repository;

import jakarta.transaction.Transactional;
import org.coursesjava.glovojava.model.OrderEntity;
import static org.junit.jupiter.api.Assertions.*;

import org.coursesjava.glovojava.model.ProductEntity;
import org.coursesjava.glovojava.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void updateById() {
        List<ProductEntity> products = new ArrayList<>();
        Long id = 4L;
        OrderEntity order = new OrderEntity();
        order.setCost(200);
        order.setDate(LocalDate.now());
        order.setProducts(products);

        OrderEntity expected = new OrderEntity();
        expected.setId(4);
        expected.setCost(200);
        expected.setDate(LocalDate.now());
        expected.setProducts(products);

        orderRepository.updateById(id, order);

        Optional<OrderEntity> foundOrder = orderRepository.findById(id);

        assertTrue(foundOrder.isPresent());
        assertEquals(expected, foundOrder.get());
    }
}
