package com.grgks.product_catalog.dto;

import com.grgks.product_catalog.core.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(

        Long id,

        String name,

        String sku,

        String description,

        BigDecimal price,

        Long quantity,

        Category category,

        Boolean available,

        LocalDateTime createdAt
) {}
