package org.coursesjava.glovojava.service;

import org.coursesjava.glovojava.model.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProductServiceMockTest {

    @MockBean
    private ProductService productService;

    @Test
    public void save() {
        ProductEntity product = new ProductEntity();
        product.setName("Apple");
        product.setCost(120);
        product.setOrder(null);

        when(productService.save(any(ProductEntity.class)))
                .thenReturn(product);
        assertEquals(product, productService.save(product));
    }

    @Test
    public void deleteProductByOrderIdAndProductName() {
        doNothing().when(productService).deleteProductByOrderIdAndProductName(anyLong(), anyString());
        productService.deleteProductByOrderIdAndProductName(1L, "Apple");
        verify(productService, times(1)).deleteProductByOrderIdAndProductName(1L, "Apple");
    }
}
