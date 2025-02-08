package com.amazon.dto.cart;

public class CartItemDTO {
    private Long productId;
    private String productName;
    private float price;
    private float discountedPrice;
    private int quantity;
    private float subTotal;

    public CartItemDTO() {
    }

    public CartItemDTO(Long productId, String productName, float price, float discountedPrice, int quantity, float subTotal) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(float discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }
}
