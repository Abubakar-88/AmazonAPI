package com.amazon.controller;

import com.amazon.dto.cart.AddToCartRequestDTO;
import com.amazon.dto.cart.CartResponseDTO;
import com.amazon.services.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(@RequestBody AddToCartRequestDTO requestDTO){
        CartResponseDTO responseDTO = cartService.addToCart(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @DeleteMapping("/remove")  // delete only a  items or product from a cart
    public ResponseEntity<CartResponseDTO> removeFromCart(
            @RequestParam Integer customerId,
            @RequestParam Integer productId) {
        CartResponseDTO responseDTO = cartService.removeFromCart(customerId, productId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable Integer customerId) {
        CartResponseDTO responseDTO = cartService.getCartByCustomer(customerId);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/clear/{customerId}")  // delete all items or product from a cart
    public ResponseEntity<Void> clearCart(@PathVariable Integer customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }
}
