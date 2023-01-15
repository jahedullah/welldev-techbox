package com.welldev.TechBox.model.dao;


import com.welldev.TechBox.model.dto.Product.ProductDto;
import com.welldev.TechBox.model.dto.Product.ProductUpdateRequestDto;
import com.welldev.TechBox.model.entity.Product;
import com.welldev.TechBox.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public interface ProductDao {

    Product createProduct(Product productToCreate);

    List<ProductDto> getProducts();

    Product deleteProduct(int pid);

    Product getProduct(int productId);

    Product updateProduct(int productId, ProductUpdateRequestDto productUpdateRequestDto);

    void updateProductCount(Product product);

    void updateProductUserList(Product product, User user);
    ArrayList<String> findAllProductName();





}
