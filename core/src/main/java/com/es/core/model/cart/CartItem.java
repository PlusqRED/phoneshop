package com.es.core.model.cart;

import com.es.core.model.phone.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    private Phone phone;
    private Long quantity;
    private Boolean wrapping;
    private String wrappingAdditional;
}
