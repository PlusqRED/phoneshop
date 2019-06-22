package com.es.phoneshop.web.controller.pages.forms;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CartPageRequestForm {
    private Map<String, String> quantities;
}
