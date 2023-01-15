package com.welldev.TechBox.model.service;


import com.welldev.TechBox.model.dto.Product.ProductDto;
import com.welldev.TechBox.model.dto.Product.ProductRegisterRequestDto;
import com.welldev.TechBox.model.dto.Product.ProductRegisterResponseDto;
import com.welldev.TechBox.model.dto.Product.ProductUpdateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;


public interface ProductService {

    ProductDto getSingleProduct(int productId);
    List<ProductDto> getProductList();

    ProductRegisterResponseDto addProduct(ProductRegisterRequestDto productRegisterRequestDto);

    ProductDto updateProduct(int productId, ProductUpdateRequestDto productUpdateRequestDto);
    ProductDto deleteProduct(int productId);
}
