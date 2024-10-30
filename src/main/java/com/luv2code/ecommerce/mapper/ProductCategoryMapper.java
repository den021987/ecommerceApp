package com.luv2code.ecommerce.mapper;
import com.luv2code.ecommerce.dto.productCategory.ProductCategoryDto;
import com.luv2code.ecommerce.dto.productCategory.CategoryWithProductsDto;
import com.luv2code.ecommerce.entity.ProductCategory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductCategoryMapper {

    private final ProductMapper productMapper;

    public ProductCategoryDto toProductCategoryDto(ProductCategory category) {
        return new ProductCategoryDto(
                category.getId(),
                category.getCategoryName()
        );
    }

    public CategoryWithProductsDto toCategoryWithProductsDto(ProductCategory category) {
        return new CategoryWithProductsDto(
                category.getId(),
                category.getCategoryName(),
                category.getProducts().stream()
                        .map(productMapper::toProductListDto)
                        .collect(Collectors.toSet())
        );
    }

}
