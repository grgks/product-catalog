package com.grgks.product_catalog.core.exceptions;

public class ProductNotFoundException extends AppGenericException {

  private static final String DEFAULT_CODE = "NotFound";

    public ProductNotFoundException(String message) {
        super("ProductNotFound" ,message);
    }
}
