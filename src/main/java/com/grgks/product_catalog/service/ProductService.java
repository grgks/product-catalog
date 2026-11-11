package com.grgks.product_catalog.service;

import com.grgks.product_catalog.core.enums.Category;
import com.grgks.product_catalog.core.exceptions.ProductNotFoundException;
import com.grgks.product_catalog.dto.ProductRequestDTO;
import com.grgks.product_catalog.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO dto);

    ProductResponseDTO getProductById(Long id)
        throws ProductNotFoundException;

    List<ProductResponseDTO> searchProduct(String name, Category category, Boolean available, String sku);

    ProductResponseDTO updateProduct(ProductRequestDTO dto , Long id)
        throws ProductNotFoundException;

    void deleteProduct(Long id)
            throws ProductNotFoundException;
    }

