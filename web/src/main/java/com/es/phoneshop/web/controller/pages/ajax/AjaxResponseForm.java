package com.es.phoneshop.web.controller.pages.ajax;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AjaxResponseForm {
    private Integer cartQuantity;
    private BigDecimal overallPrice;
    private String errorMessage;
}
