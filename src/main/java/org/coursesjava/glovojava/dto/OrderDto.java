package org.coursesjava.glovojava.dto;

import lombok.Data;
import org.coursesjava.glovojava.model.ProductEntity;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {
    private Integer id;
    private LocalDate date;
    private Integer cost;
    private List<ProductEntity> products;
}
