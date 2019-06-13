package com.es.phoneshop.web.controller.pages.forms;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AjaxResponseForm {
    private Integer cartQuantity;
    private String overallPrice;
    private String errorMessage;
}
