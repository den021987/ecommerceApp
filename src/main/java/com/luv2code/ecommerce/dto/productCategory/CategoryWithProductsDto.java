package com.luv2code.ecommerce.dto.productCategory;

import com.luv2code.ecommerce.dto.product.ProductListDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryWithProductsDto {
    private Long id;
    private String categoryName;
    private Set<ProductListDto> products;  // Assuming you have ProductDto defined
}