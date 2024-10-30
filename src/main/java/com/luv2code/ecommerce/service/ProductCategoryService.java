package com.luv2code.ecommerce.service;

import com.luv2code.ecommerce.dto.productCategory.ProductCategoryDto;
import com.luv2code.ecommerce.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategoryDto> getAllCategories();

}
