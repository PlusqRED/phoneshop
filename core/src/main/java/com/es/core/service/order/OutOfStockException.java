package com.es.core.service.order;

public class OutOfStockException extends Exception {
    private Long outOfStockPhoneId;

    public OutOfStockException(Long outOfStockPhoneId) {
        this.outOfStockPhoneId = outOfStockPhoneId;
    }

    public Long getOutOfStockPhoneId() {
        return outOfStockPhoneId;
    }

    public void setOutOfStockPhoneId(Long outOfStockPhoneId) {
        this.outOfStockPhoneId = outOfStockPhoneId;
    }
}
