package com.welldev.TechBox.model.service.impl;

import com.welldev.TechBox.model.dao.ProductDao;
import com.welldev.TechBox.model.dto.Product.ProductDto;
import com.welldev.TechBox.model.dto.Product.ProductRegisterRequestDto;
import com.welldev.TechBox.model.dto.Product.ProductRegisterResponseDto;
import com.welldev.TechBox.model.dto.Product.ProductUpdateRequestDto;
import com.welldev.TechBox.model.entity.Product;
import com.welldev.TechBox.model.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductDao productDao;


    @Override
    public ProductDto getSingleProduct(int productId) {
        Optional<Product> product = Optional.ofNullable(productDao.getProduct(productId));
        if (product.isPresent()) {
            return ProductDto.builder()
                    .id(product.get().getId())
                    .name(product.get().getName())
                    .description(product.get().getDescription())
                    .price(product.get().getPrice())
                    .productCount(product.get().getProductCount())
                    .build();
        }else {
            return ProductDto.builder().build();
        }
    }

    @Override
    public List<ProductDto> getProductList() {
        return productDao.getProducts();
    }


    @Override
    public ProductRegisterResponseDto addProduct(ProductRegisterRequestDto productRegisterRequestDto) {
        List<Product> productNameList = productDao.findAllProductName();
        if (!productNameList.contains(productRegisterRequestDto.getName())) {
            Product productToCreate = new Product(
                    productRegisterRequestDto.getName(),
                    productRegisterRequestDto.getDescription(),
                    productRegisterRequestDto.getPrice(),
                    productRegisterRequestDto.getProductCount());

            Product product = productDao.createProduct(productToCreate);
            return new ProductRegisterResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getProductCount()
            );
        }else {
            return new ProductRegisterResponseDto();
        }
    }


    @Override
    public ProductDto updateProduct(int productId, ProductUpdateRequestDto productUpdateRequestDto) {
        Product productToUpdate = productDao.updateProduct(productId,productUpdateRequestDto);
        return new ProductDto(
                productToUpdate.getId(),
                productToUpdate.getName(),
                productToUpdate.getDescription(),
                productToUpdate.getPrice(),
                productToUpdate.getProductCount()
        );
    }


    @Override
    public ProductDto deleteProduct(int productId) {
        Product productToDelete = productDao.deleteProduct(productId);
        return new ProductDto(
                productToDelete.getId(),
                productToDelete.getName(),
                productToDelete.getDescription(),
                productToDelete.getPrice(),
                productToDelete.getProductCount()
        );
    }


}
