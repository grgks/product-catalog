package com.grgks.product_catalog.core.exceptions;

public class ProductAlreadyExistsException extends AppGenericException {

  private static final String DEFAULT_CODE = "ProductAlreadyExists";

    public ProductAlreadyExistsException(String message) {
        super(DEFAULT_CODE,  message);
    }
}
