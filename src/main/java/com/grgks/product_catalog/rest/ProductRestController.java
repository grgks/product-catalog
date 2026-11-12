package com.grgks.product_catalog.rest;

import com.grgks.product_catalog.core.enums.Category;
import com.grgks.product_catalog.core.exceptions.ProductAlreadyExistsException;
import com.grgks.product_catalog.core.exceptions.ProductNotFoundException;
import com.grgks.product_catalog.dto.ProductRequestDTO;
import com.grgks.product_catalog.dto.ProductResponseDTO;
import com.grgks.product_catalog.dto.ResponseMessageDTO;
import com.grgks.product_catalog.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO dto) throws ProductAlreadyExistsException {

        return new ResponseEntity<>(productService.createProduct(dto), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @PathVariable Long id) throws ProductNotFoundException {

        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String sku) {

        return new ResponseEntity<>(
                productService.searchProduct(name, category, available, sku), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
             @Valid @RequestBody ProductRequestDTO dto,
             @PathVariable Long id) throws ProductNotFoundException{

        return new ResponseEntity<>(
                productService.updateProduct(dto, id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> deleteProduct(
            @PathVariable Long id) throws ProductNotFoundException{

         productService.deleteProduct(id);

        return new ResponseEntity<>(ResponseMessageDTO.success("Product deleted successfully with id: " + id), HttpStatus.OK);
    }
}

