package com.luv2code.ecommerce.service;

import com.luv2code.ecommerce.dto.product.ProductDetailDto;
import com.luv2code.ecommerce.dto.product.ProductDto;
import com.luv2code.ecommerce.dto.product.ProductListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductDetailDto getProductById(Long id);

    Page<ProductListDto> getAllProducts(Pageable pageable);

    ProductDetailDto createProduct(ProductDto productDto);

    Page<ProductListDto> getProductsByCategoryId(Long id, Pageable pageable);

    Page<ProductListDto> getProductsByName(String name, Pageable pageable);

//    ProductDetailDto updateProduct(Long id, ProductDto productDto);

//    void deleteProduct(Long id);
}
