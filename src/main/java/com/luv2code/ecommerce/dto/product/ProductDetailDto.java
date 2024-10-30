package com.luv2code.ecommerce.dto.product;

import java.math.BigDecimal;
import java.util.Date;

import com.luv2code.ecommerce.dto.productCategory.ProductCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private String imageUrl;
    private ProductCategoryDto category;
    private String sku;
    private boolean active;
    private int unitsInStock;
    private Date dateCreated;
    private Date lastUpdated;
}
