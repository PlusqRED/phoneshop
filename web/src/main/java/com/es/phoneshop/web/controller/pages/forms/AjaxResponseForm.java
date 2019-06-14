package com.es.phoneshop.web.controller.pages.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AjaxResponseForm {
    private Integer cartQuantity;
    private String overallPrice;
    private String errorMessage;
}
