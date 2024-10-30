package com.luv2code.ecommerce.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDto {

    private Long id;
    private String name;
    private BigDecimal unitPrice;
    private String imageUrl;
    private boolean active;
}
