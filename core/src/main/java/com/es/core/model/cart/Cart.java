package com.es.core.model.cart;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private List<CartItem> items;

    private boolean needRecalculate = true;

    private BigDecimal overallPrice;

    public Cart() {
        overallPrice = BigDecimal.ZERO;
        items = new ArrayList<>();
    }

    public BigDecimal getOverallPrice() {
        if (needRecalculate) {
            recalculateOverallPrice();
            needRecalculate = false;
        }
        return overallPrice;
    }

    private void recalculateOverallPrice() {
        overallPrice = items.stream()
                .map(cartItem ->
                        cartItem.getPhone().getPrice()
                                .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addCartItem(CartItem cartItem) {
        Optional<CartItem> optionalCartItem = items.stream()
                .filter(item -> item.getPhone().getId().equals(cartItem.getPhone().getId()))
                .findAny();
        if (optionalCartItem.isPresent()) {
            CartItem item = optionalCartItem.get();
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
        } else {
            items.add(cartItem);
        }
        needRecalculate = true;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Integer getProductQuantity() {
        return items.stream()
                .map(CartItem::getQuantity)
                .reduce(0L, Long::sum)
                .intValue();
    }

    public Long getQuantityByProductId(Long productId) {
        return items.stream()
                .filter(cartItem -> cartItem.getPhone().getId().equals(productId))
                .map(CartItem::getQuantity)
                .findAny()
                .orElse(0L);
    }

    public void setQuantityByProductId(Long productId, Long quantity) {
        items.stream()
                .filter(item -> item.getPhone().getId().equals(productId))
                .findAny()
                .ifPresent(item -> item.setQuantity(quantity));
        needRecalculate = true;
    }

    public void remove(Long phoneId) {
        items.stream()
                .filter(cartItem -> cartItem.getPhone().getId().equals(phoneId))
                .findAny()
                .ifPresent(items::remove);
        needRecalculate = true;
    }

    public void clear() {
        items.clear();
        overallPrice = BigDecimal.ZERO;
    }
}
