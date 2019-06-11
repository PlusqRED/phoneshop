package com.es.core.model.cart;

import com.es.core.model.phone.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Phone phone;
    private Long quantity;
}
