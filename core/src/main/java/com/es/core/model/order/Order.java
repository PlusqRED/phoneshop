package com.es.core.model.order;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "orderItems")
public class Order {
    private Long id;
    private List<OrderItem> orderItems;
    /**
     * A sum of order item prices;
     */
    private BigDecimal subtotal;
    private BigDecimal deliveryPrice;
    private BigDecimal overallWrappingPrice;
    /**
     * <code>subtotal</code> + <code>deliveryPrice</code>
     */
    private BigDecimal totalPrice;

    private String firstName;
    private String lastName;
    private String deliveryAddress;
    private String contactPhoneNo;

    private String additionalInformation;

    private LocalDateTime date;

    private OrderStatus status;
}
