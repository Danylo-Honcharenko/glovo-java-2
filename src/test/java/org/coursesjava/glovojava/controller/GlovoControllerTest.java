package org.coursesjava.glovojava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.coursesjava.glovojava.model.OrderEntity;
import org.coursesjava.glovojava.model.ProductEntity;
import org.coursesjava.glovojava.repository.ProductRepository;
import org.coursesjava.glovojava.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

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
    private ProductRepository productRepository;
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
        when(orderService.findById(anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(get("/api/orders/{id}", 1))
                .andExpect(status().isFound());
    }

    @Test
    public void getByIdNotFound() throws Exception {
        when(orderService.findById(10L))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        mockMvc.perform(get("/api/orders/{id}", 10))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrder() throws Exception {
        OrderEntity order = new OrderEntity();
        order.setDate(LocalDate.now());
        order.setCost(150);

        when(orderService.findById(anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        doNothing().when(orderService).updateById(anyLong(), any(OrderEntity.class));
        mockMvc.perform(put("/api/orders/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk());
        verify(orderService, times(1)).updateById(1L, order);
    }

    @Test
    public void addProductToOrder() throws Exception {
        ProductEntity product = new ProductEntity();
        product.setId(1);
        product.setName("Apple");
        product.setCost(120);
        product.setOrder(null);

        when(orderService.findById(anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(patch("/api/orders/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProduct() throws Exception {
        when(orderService.findById(anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        doNothing().when(productRepository).deleteProductByIdAndName(anyLong(), anyString());
        mockMvc.perform(delete("/api/orders/{id}/product/{name}", 1, "Apple"))
                .andExpect(status().isOk());
        verify(productRepository, times(1)).deleteProductByIdAndName(1L, "Apple");
    }

    @Test
    public void deleteOrder() throws Exception {
        doNothing().when(orderService).deleteById(anyLong());
        mockMvc.perform(delete("/api/orders/{id}", 1))
                .andReturn();
        verify(orderService, times(1)).deleteById(1L);
    }
}
