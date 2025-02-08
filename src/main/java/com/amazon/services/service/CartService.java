package com.amazon.services.service;

import com.amazon.dto.cart.AddToCartRequestDTO;
import com.amazon.dto.cart.CartResponseDTO;

public interface CartService {
    CartResponseDTO addToCart(AddToCartRequestDTO requestDTO);
    CartResponseDTO removeFromCart(Integer customerId, Integer productId);
    CartResponseDTO getCartByCustomer(Integer customerId);
    void clearCart(Integer customerId);
}
