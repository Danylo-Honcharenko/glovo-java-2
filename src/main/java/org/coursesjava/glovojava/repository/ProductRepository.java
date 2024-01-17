package org.coursesjava.glovojava.repository;

import jakarta.transaction.Transactional;
import org.coursesjava.glovojava.model.ProductEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ProductEntity p WHERE p.order.id = :id AND p.name = :name")
    void deleteProductByIdAndName(Long id, String name);
}
