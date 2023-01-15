package com.welldev.TechBox.model.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductRegisterResponseDto {
    private int id;
    private String name;
    private String description;
    private double price;
    private int productCount;
}
