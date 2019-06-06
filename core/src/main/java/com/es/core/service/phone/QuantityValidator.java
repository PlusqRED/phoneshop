package com.es.core.service.phone;

public interface QuantityValidator {
    boolean validate(Long productId, Long quantity);
}
