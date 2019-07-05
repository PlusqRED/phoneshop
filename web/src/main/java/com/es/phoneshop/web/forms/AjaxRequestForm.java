package com.es.phoneshop.web.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AjaxRequestForm {
    private Long productId;
    private String quantity;
}
