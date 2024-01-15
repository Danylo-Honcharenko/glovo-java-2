package org.coursesjava.glovojava.repository;

import jakarta.transaction.Transactional;
import org.coursesjava.glovojava.model.OrderEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    @Modifying
    @Transactional
    // :#{#order.cost} - SpEL
    @Query("UPDATE OrderEntity o SET o.cost = :#{#order.cost}, o.date = :#{#order.date} WHERE o.id = :id")
    void updateById(@Param("id") Long id, @Param("order") OrderEntity order);
}
