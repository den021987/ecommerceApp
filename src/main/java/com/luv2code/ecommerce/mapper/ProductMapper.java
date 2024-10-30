package com.luv2code.ecommerce.mapper;

import com.luv2code.ecommerce.dto.product.ProductDto;
import com.luv2code.ecommerce.dto.product.ProductDetailDto;
import com.luv2code.ecommerce.dto.product.ProductListDto;
import com.luv2code.ecommerce.dto.productCategory.ProductCategoryDto;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toProductDto(Product product) {
        return new ProductDto(
                product.getName(),
                product.getDescription(),
                product.getUnitPrice(),
                product.getImageUrl(),
                product.getCategory().getId(),
                product.getSku(),
                product.isActive(),
                product.getUnitsInStock()
        );
    }

    public ProductDetailDto toProductDetailDto(Product product) {
        return new ProductDetailDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getUnitPrice(),
                product.getImageUrl(),
                toProductCategoryDto(product.getCategory()),
                product.getSku(),
                product.isActive(),
                product.getUnitsInStock(),
                product.getDateCreated(),
                product.getLastUpdated()
        );
    }

    public ProductListDto toProductListDto(Product product) {
        return new ProductListDto(
                product.getId(),
                product.getName(),
                product.getUnitPrice(),
                product.getImageUrl(),
                product.isActive()
        );
    }

    private ProductCategoryDto toProductCategoryDto(ProductCategory category) {
        return new ProductCategoryDto(
                category.getId(),
                category.getCategoryName()
        );
    }

    public void updateProductFromDto(ProductDto productDto, Product product) {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setSku(productDto.getSku());
        product.setActive(productDto.isActive());
        product.setUnitsInStock(productDto.getUnitsInStock());
        // Category is typically set separately in the service layer, as shown in the service code
    }

    // Example of converting ProductDto to Product
    public Product toProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setSku(productDto.getSku());
        product.setActive(productDto.isActive());
        product.setUnitsInStock(productDto.getUnitsInStock());
        // Category is typically set separately in the service layer, as shown in the service code
        return product;
    }
}
