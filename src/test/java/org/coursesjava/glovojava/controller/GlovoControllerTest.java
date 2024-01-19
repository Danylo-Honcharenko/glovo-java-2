package org.coursesjava.glovojava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.coursesjava.glovojava.model.OrderEntity;
import org.coursesjava.glovojava.model.ProductEntity;
import org.coursesjava.glovojava.service.OrderService;
import org.coursesjava.glovojava.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GlovoController.class)
public class GlovoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createSuccessfully() throws Exception {
        OrderEntity order = new OrderEntity();
        order.setId(5);
        order.setDate(LocalDate.now());
        order.setCost(0);

        mockMvc.perform(post("/api/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createWithOutBody() throws Exception {
        mockMvc.perform(post("/api/orders/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getById() throws Exception {
        OrderEntity order = new OrderEntity();
        order.setId(1);
        order.setDate(LocalDate.now());
        order.setCost(0);

        when(orderService.findById(anyLong()))
                .thenReturn(Optional.of(order));
        mockMvc.perform(get("/api/orders/{id}", 1))
                .andExpect(status().isFound());
    }

    @Test
    public void getByIdNotFound() throws Exception {
        when(orderService.findById(anyLong()))
                .thenReturn(Optional.empty());
        mockMvc.perform(get("/api/orders/{id}", 10))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrder() throws Exception {
        OrderEntity expected = new OrderEntity();
        expected.setId(1);
        expected.setDate(LocalDate.now());
        expected.setCost(150);

        OrderEntity actual = new OrderEntity();
        actual.setDate(LocalDate.now());
        actual.setCost(150);

        when(orderService.findById(anyLong()))
                .thenReturn(Optional.of(expected));
        doNothing().when(orderService).updateById(anyLong(), any(OrderEntity.class));
        mockMvc.perform(put("/api/orders/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actual)))
                .andExpect(status().isFound());
        verify(orderService, times(1)).updateById(1L, actual);
    }

    @Test
    public void addProductToOrder() throws Exception {
        OrderEntity expected = new OrderEntity();
        expected.setId(1);
        expected.setDate(LocalDate.now());
        expected.setCost(150);

        ProductEntity actual = new ProductEntity();
        actual.setName("Apple");
        actual.setCost(120);
        actual.setOrder(null);

        when(orderService.findById(anyLong()))
                .thenReturn(Optional.of(expected));
        mockMvc.perform(patch("/api/orders/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(actual)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProduct() throws Exception {
        OrderEntity expected = new OrderEntity();
        expected.setId(1);
        expected.setDate(LocalDate.now());
        expected.setCost(150);

        when(orderService.findById(anyLong()))
                .thenReturn(Optional.of(expected));
        doNothing().when(productService).deleteProductByOrderIdAndProductName(anyLong(), anyString());
        mockMvc.perform(delete("/api/orders/{id}/product/{name}", 1, "Apple"))
                .andExpect(status().isOk());
        verify(productService, times(1)).deleteProductByOrderIdAndProductName(1L, "Apple");
    }

    @Test
    public void deleteOrder() throws Exception {
        doNothing().when(orderService).deleteById(anyLong());
        mockMvc.perform(delete("/api/orders/{id}", 1))
                .andReturn();
        verify(orderService, times(1)).deleteById(1L);
    }
}
