package com.es.phoneshop.web.controller.pages.forms;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Data
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class CartPageResponseForm {
    private List<String> errors;
    private boolean viewed = true;

    public void updateErrors(List<ObjectError> allErrors) {
        errors = new ArrayList<>();
        allErrors.forEach(error -> errors.add(error.getDefaultMessage()));
        viewed = false;
    }
}
