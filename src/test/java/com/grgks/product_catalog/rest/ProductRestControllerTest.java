package com.grgks.product_catalog.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //check 201 created
    @Test
    void createProduct_WithValidData_Returns201() throws Exception {
        String json = """
                {
                    "name": "Test Laptop",
                    "description": "A test description",
                    "price": 999.99,
                    "quantity": 10,
                    "category": "ELECTRONICS",
                    "available": true
                }
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Laptop"))
                .andExpect(jsonPath("$.category").value("ELECTRONICS"))
                .andExpect(jsonPath("$.sku").isNotEmpty());
    }

    //check 400 Bad Request
    @Test
    void createProduct_WithInvalidData_Returns400() throws Exception {
        String json = """
                {
                    "name": "",
                    "description": "",
                    "category": null
                }
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    // check Not Found 404
    @Test
    void getProductById_NotFound_Returns404() throws Exception {
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").exists());
    }

    //check search by category "Home" 200 returns
    @Test
    void searchProducts_ByCategory_ReturnsFilteredResults() throws Exception {
        // create
        String json = """
                {
                    "name": "Search Test Product",
                    "description": "A product for search testing",
                    "price": 49.32,
                    "quantity": 5,
                    "category": "HOME",
                    "available": true
                }
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        // search with category
        mockMvc.perform(get("/api/products")
                        .param("category", "HOME"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("HOME"));
    }
}