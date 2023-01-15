package com.welldev.TechBox.controllers;

import com.welldev.TechBox.model.dao.ProductDao;
import com.welldev.TechBox.model.dto.Product.ProductDto;
import com.welldev.TechBox.model.dto.Product.ProductRegisterRequestDto;
import com.welldev.TechBox.model.dto.Product.ProductRegisterResponseDto;
import com.welldev.TechBox.model.dto.Product.ProductUpdateRequestDto;
import com.welldev.TechBox.model.service.ProductService;
import com.welldev.TechBox.string.PRODUCT_URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductDao productDao;
    private final ProductService productService;

    @GetMapping(PRODUCT_URL.PRODUCT_WITH_ID)
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable int productId) {
        return ResponseEntity.ok(productService.getSingleProduct(productId));
    }


    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProductList() {
        return ResponseEntity.ok(productService.getProductList());

    }


    @PostMapping()
    public ResponseEntity<ProductRegisterResponseDto>
    addProduct(@Valid @RequestBody
               ProductRegisterRequestDto productRegisterRequestDto) {
            return ResponseEntity.ok(productService.addProduct(productRegisterRequestDto));

    }


    @PutMapping(value = PRODUCT_URL.PRODUCT_UPDATE_BY_ID)
    public ResponseEntity<ProductDto>
    updateProduct(@Valid @PathVariable int productId,
                  @Valid @RequestBody ProductUpdateRequestDto productUpdateRequestDto) throws NullPointerException {

            ProductDto productDto =
                    productService.updateProduct(productId, productUpdateRequestDto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(productDto);

    }


    @DeleteMapping(value = PRODUCT_URL.PRODUCT_DELETE_BY_ID)
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable int productId) throws NullPointerException {
        try {
            ProductDto productDto =
                    productService.deleteProduct(productId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(productDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}

