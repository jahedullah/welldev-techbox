package com.welldev.TechBox.model.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Builder
public class ProductDto {
    private int id;
    private String name;
    private String description;
    private double price;
    private int productCount;

}
