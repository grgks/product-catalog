package com.grgks.product_catalog.dto;

import java.util.Map;

public record ResponseMessageDTO(

        String status,
        String message,
        Map<String, String> errors
    ) {

    public static ResponseMessageDTO error(String message) {
        return new ResponseMessageDTO("error", message, null);
    }

    public static ResponseMessageDTO success(String message) {
        return new ResponseMessageDTO("success", message, null);
    }

    public static ResponseMessageDTO validationError(Map<String, String> errors) {
        return new ResponseMessageDTO("error", "Validation failed", errors);
    }
}
