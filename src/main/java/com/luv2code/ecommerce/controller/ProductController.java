package com.luv2code.ecommerce.controller;

import com.luv2code.ecommerce.dto.product.ProductDto;
import com.luv2code.ecommerce.dto.product.ProductListDto;
import com.luv2code.ecommerce.dto.productCategory.ProductCategoryDto;
import com.luv2code.ecommerce.service.ProductCategoryService;
import com.luv2code.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
@Controller
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Long page,
                             Model model){
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("currentPage", page);

        return "products/view-product";
    }

    @GetMapping
    public String getProducts(@RequestParam(required = false) Long categoryId,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {

        int safePage = Math.max(page, 0);
        Page<ProductListDto> productPage;
        if (categoryId != null) {
            productPage = productService.getProductsByCategoryId(categoryId, PageRequest.of(safePage, size));
        } else {
            productPage = productService.getAllProducts(PageRequest.of(safePage, size));
        }

        model.addAttribute("categories", productCategoryService.getAllCategories());
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", safePage);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("categoryId", categoryId); // Keep track of the categoryId
        model.addAttribute("size", size); // Include size if you want dynamic control over the number of items per page

        return "products/product";
    }

    @GetMapping("/category/{id}")
    public String getProductsByCategoryId(@PathVariable("id") Long categoryId, Model model,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page, size);

        Page<ProductListDto> productPage = productService.getProductsByCategoryId(categoryId, pageable);
        List<ProductCategoryDto> categories = productCategoryService.getAllCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("categoryId", categoryId);

        return "products/product";
    }
    @GetMapping("/search")
    public String searchProducts(@RequestParam String name,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductListDto> productPage = productService.getProductsByName(name, pageable);

//        model.addAttribute("categories", productCategoryService.getAllCategories());
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("searchQuery", name); // Include the search query to prepopulate the search box
        model.addAttribute("size", size); // Include size for pagination controls

        return "products/product"; // The Thymeleaf template to display search results
    }


    @GetMapping("/create")
    public String showCreateProductForm(Model model) {
        ProductDto productDto = new ProductDto();
        productDto.setCategoryId(null);

        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", productCategoryService.getAllCategories()); // Assuming you have a method to fetch categories
        return "products/product-create"; // The name of the Thymeleaf template
    }


    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductDto productDto,
                                BindingResult bindingResult,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (imageFile.isEmpty()) {
            bindingResult.rejectValue("imageUrl", "NotBlank", "Please upload an image");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", productCategoryService.getAllCategories());
            return "products/product-create"; // Return back to form with error messages
        }
        try {
            if (!imageFile.isEmpty()) {
                String uploadDir = "spring-boot-ecommerce/src/main/resources/static/images/products/";

                // Create the directory if it doesn't exist
                File uploadDirectory = new File(uploadDir);
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }

                String originalFilename = imageFile.getOriginalFilename();
                String filePath = uploadDir + originalFilename;
                File destinationFile = new File(filePath);
                Files.copy(imageFile.getInputStream(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                productDto.setImageUrl("/images/products/" + originalFilename);  // Adjusted URL
            }

            productService.createProduct(productDto);

//            model.addAttribute("successMessage", "Product was successfully created!");

            redirectAttributes.addFlashAttribute("successMessage", "Product was successfully created!");

            return "redirect:/products";  // Redirect to the product list page

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error saving the product image.");
            return "products/product-create";
        }
    }
}
