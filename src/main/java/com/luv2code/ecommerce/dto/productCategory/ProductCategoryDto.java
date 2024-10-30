package com.luv2code.ecommerce.dto.productCategory;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCategoryDto {
    private Long id;
    private String categoryName;
}