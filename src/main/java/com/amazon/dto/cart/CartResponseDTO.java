package com.amazon.dto.cart;

import java.util.List;

public class CartResponseDTO {
    private Long cartId;
    private Long customerId;
    private float totalPrice;
    private List<CartItemDTO> items;

    public CartResponseDTO() {
    }

    public CartResponseDTO(Long cartId, Long customerId, float totalPrice, List<CartItemDTO> items) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }
}
