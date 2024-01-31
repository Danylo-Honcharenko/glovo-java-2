package org.coursesjava.glovojava.service;

import org.coursesjava.glovojava.model.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
public class OrderServiceMockTest {

    @MockBean
    private OrderService orderService;

    @Test
    public void save() {
        OrderEntity actual = new OrderEntity();
        actual.setId(1);
        actual.setDate(LocalDate.now());
        actual.setCost(0);

        OrderEntity expected = new OrderEntity();
        expected.setId(1);
        expected.setDate(LocalDate.now());
        expected.setCost(0);

        when(orderService.save(any(OrderEntity.class)))
                .thenReturn(expected);
        assertEquals(expected, orderService.save(actual));
    }

    @Test
    public void findById() {
        OrderEntity expected = new OrderEntity();
        expected.setId(1);
        expected.setDate(LocalDate.now());
        expected.setCost(0);

        when(orderService.findById(anyLong()))
                .thenReturn(expected);
        OrderEntity order = orderService.findById(1L);

        assertEquals(expected, order);
    }

    @Test
    public void updateById() {
        OrderEntity order = new OrderEntity();
        order.setDate(LocalDate.now());
        order.setCost(150);

        doNothing().when(orderService).updateById(anyLong(), any(OrderEntity.class));
        orderService.updateById(1L, order);
        verify(orderService, times(1)).updateById(1L, order);
    }

    @Test
    public void deleteById() {
        doNothing().when(orderService).deleteById(anyLong());
        orderService.deleteById(1L);
        verify(orderService, times(1)).deleteById(1L);
    }
}
