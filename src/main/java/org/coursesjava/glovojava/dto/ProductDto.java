package org.coursesjava.glovojava.dto;

import lombok.Data;
import org.coursesjava.glovojava.model.OrderEntity;

@Data
public class ProductDto {
    private Integer id;
    private String name;
    private Integer cost;
    private OrderEntity order;
}
