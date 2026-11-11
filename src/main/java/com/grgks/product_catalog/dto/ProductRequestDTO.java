package com.grgks.product_catalog.dto;

import com.grgks.product_catalog.core.enums.Category;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "Product name is required")
        String name,

        @NotBlank
        @Size(min = 3 , max = 3000,
        message = "Description must be between 3 - 3000 characters")
        String description,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        @Digits(integer = 8, fraction = 2,
        message = "Price supports up to 2 decimal digits")
        BigDecimal price,

        @NotNull(message = "Quantity is required")
        Long quantity,

        @NotNull(message = "Category is required")
        Category category,

        Boolean available
) {}
