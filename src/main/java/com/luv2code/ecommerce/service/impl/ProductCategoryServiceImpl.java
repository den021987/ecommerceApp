package com.luv2code.ecommerce.service.impl;

import com.luv2code.ecommerce.dto.product.ProductListDto;
import com.luv2code.ecommerce.dto.productCategory.ProductCategoryDto;
import com.luv2code.ecommerce.entity.ProductCategory;
import com.luv2code.ecommerce.mapper.ProductCategoryMapper;
import com.luv2code.ecommerce.repository.ProductCategoryRepository;
import com.luv2code.ecommerce.service.ProductCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategoryDto> getAllCategories() {

        List<ProductCategory> productCategories = productCategoryRepository.findAll();

        return productCategories.stream()
                                .map(productCategoryMapper::toProductCategoryDto)
                                .collect(Collectors.toList());
    }
}
