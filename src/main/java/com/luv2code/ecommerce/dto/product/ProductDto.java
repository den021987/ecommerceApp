package com.luv2code.ecommerce.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Price cannot be null")
    private BigDecimal unitPrice;

    private String imageUrl;

    @NotNull(message = "You need to select a category")
    private Long categoryId;

    @NotBlank(message = "Sku of the product cannot be null")
    private String sku;

    private boolean active;

    @NotNull(message = "Stock cannot be null")
    private Integer unitsInStock;

}
