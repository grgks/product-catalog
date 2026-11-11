package com.grgks.product_catalog.service;

import com.grgks.product_catalog.core.enums.Category;
import com.grgks.product_catalog.core.exceptions.ProductAlreadyExistsException;
import com.grgks.product_catalog.core.exceptions.ProductNotFoundException;
import com.grgks.product_catalog.dto.ProductRequestDTO;
import com.grgks.product_catalog.dto.ProductResponseDTO;
import com.grgks.product_catalog.mapper.Mapper;
import com.grgks.product_catalog.model.Product;
import com.grgks.product_catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final Mapper mapper;



    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        if (productRepository.existsByName(dto.name())) {
            throw new ProductAlreadyExistsException("Product with name '" + dto.name() + "' already exists");
        }

        Product product = mapper.mapToProductEntity(dto);
        product.setSku(UUID.randomUUID().toString().substring(0, 6));
        product.setAvailable(dto.available() != null ? dto.available() : false);

        Product savedProduct = productRepository.save(product);
        return mapper.mapToProductResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO getProductById(Long id)
            throws ProductNotFoundException {

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return mapper.mapToProductResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> searchProduct(String name, Category category, Boolean available, String sku) {
        return productRepository.searchProducts(name, category, available, sku)
                .stream()
                .map(mapper::mapToProductResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO updateProduct(ProductRequestDTO dto, Long id) throws ProductNotFoundException {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        existingProduct.setName(dto.name());
        existingProduct.setDescription(dto.description());
        existingProduct.setPrice(dto.price());
        existingProduct.setQuantity(dto.quantity());
        existingProduct.setAvailable(dto.available() != null ? dto.available() : existingProduct.getAvailable());


        Product savedproduct = productRepository.save(existingProduct);
        return mapper.mapToProductResponseDTO(savedproduct);

    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    //Helper methods

}


