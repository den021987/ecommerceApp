package com.luv2code.ecommerce.service.impl;

import com.luv2code.ecommerce.dto.product.ProductDetailDto;
import com.luv2code.ecommerce.dto.product.ProductDto;
import com.luv2code.ecommerce.dto.product.ProductListDto;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.mapper.ProductMapper;
import com.luv2code.ecommerce.repository.ProductCategoryRepository;
import com.luv2code.ecommerce.repository.ProductRepository;
import com.luv2code.ecommerce.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductDetailDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toProductDetailDto(product);
    }

    @Override
    public Page<ProductListDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toProductListDto);
    }

    @Override
    public ProductDetailDto createProduct(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);

        product.setCategory(productCategoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDto.getCategoryId())));

        productRepository.save(product);

        return productMapper.toProductDetailDto(product);
    }


    @Override
    public Page<ProductListDto> getProductsByCategoryId(Long id, Pageable pageable) {

        return productRepository.findByCategoryId(id, pageable).map(productMapper::toProductListDto);
    }

    @Override
    public Page<ProductListDto> getProductsByName(String name, Pageable pageable) {
        return productRepository.findByNameContaining(name, pageable).map(productMapper::toProductListDto);
    }

}
