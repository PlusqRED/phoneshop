package com.es.core.model.order;

import com.es.core.model.phone.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    private Long id;
    private Phone phone;
    private Long orderId;
    private Long quantity;
}
