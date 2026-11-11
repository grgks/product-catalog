package com.grgks.product_catalog.mapper;


import com.grgks.product_catalog.dto.ProductRequestDTO;
import com.grgks.product_catalog.dto.ProductResponseDTO;
import com.grgks.product_catalog.model.Product;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Product mapToProductEntity(ProductRequestDTO dto){

        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setCategory(dto.category());
        product.setAvailable(dto.available() );
        return product;
    }

    public ProductResponseDTO mapToProductResponseDTO(Product product){

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                product.getAvailable(),
                product.getCreatedAt()
        );
    }
}
