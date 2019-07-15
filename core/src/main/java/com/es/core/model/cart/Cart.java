package com.es.core.model.cart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@PropertySource("classpath:properties/order.properties")
public class Cart {
    private final List<CartItem> items;

    private boolean needRecalculate = true;

    private BigDecimal overallPrice;

    private BigDecimal overallWrappingPrice;

    @Value("${wrapping.price}")
    private Double wrappingPrice;

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
        overallWrappingPrice = BigDecimal.ZERO;
        items.stream().filter(CartItem::getWrapping).forEach(cartItem ->
                overallWrappingPrice = overallWrappingPrice.add(
                        BigDecimal.valueOf(wrappingPrice)
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
                )
        );
        overallPrice = items.stream()
                .map(cartItem ->
                        cartItem.getPhone().getPrice()
                                .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(overallWrappingPrice);
    }

    public void addCartItem(CartItem cartItem) {
        Optional<CartItem> optionalCartItem = items.stream()
                .filter(item -> item.getPhone().getId().equals(cartItem.getPhone().getId()))
                .findAny();
        if (optionalCartItem.isPresent()) {
            CartItem item = optionalCartItem.get();
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            item.setWrapping(cartItem.getWrapping());
            item.setWrappingAdditional(cartItem.getWrappingAdditional());
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

    public String getModelById(Long id) {
        return items.stream()
                .filter(item -> item.getPhone().getId().equals(id))
                .findAny()
                .map(item -> item.getPhone().getModel())
                .orElse(null);
    }

    public BigDecimal getOverallWrappingPrice() {
        return overallWrappingPrice;
    }
}
