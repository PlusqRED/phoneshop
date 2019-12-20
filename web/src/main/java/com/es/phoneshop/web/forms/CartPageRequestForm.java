package com.es.phoneshop.web.forms;

import lombok.Data;

import java.util.Map;

@Data
public class CartPageRequestForm {
    private Map<String, String> quantities;
}
