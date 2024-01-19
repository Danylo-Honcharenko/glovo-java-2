package org.coursesjava.glovojava.service;

import lombok.RequiredArgsConstructor;
import org.coursesjava.glovojava.model.ProductEntity;
import org.coursesjava.glovojava.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }

    public void deleteProductByOrderIdAndProductName(Long id, String name) {
        productRepository.deleteProductByOrderIdAndProductName(id, name);
    }
}
