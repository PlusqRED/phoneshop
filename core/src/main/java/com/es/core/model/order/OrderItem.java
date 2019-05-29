package com.es.core.model.order;

import com.es.core.model.phone.Phone;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItem {
    private Long id;
    private Phone phone;
    private Order order;
    private Long quantity;
}
