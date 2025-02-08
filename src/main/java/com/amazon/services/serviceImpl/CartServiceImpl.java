package com.amazon.services.serviceImpl;

import com.amazon.dto.cart.AddToCartRequestDTO;
import com.amazon.dto.cart.CartResponseDTO;
import com.amazon.entity.Cart;
import com.amazon.entity.CartItem;
import com.amazon.entity.Customer;
import com.amazon.entity.Product;
import com.amazon.exception.ResourceNotFoundException;
import com.amazon.repository.CartItemRepository;
import com.amazon.repository.CartRepository;
import com.amazon.repository.CustomerRepository;
import com.amazon.repository.ProductRepository;
import com.amazon.services.service.CartService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Transactional
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;


//    @Override
//    public CartResponseDTO addToCart(AddToCartRequestDTO requestDTO) {
//       Integer customerId = requestDTO.getCustomerId();
//       Integer productId = requestDTO.getProductId();
//       int quantity = requestDTO.getQuantity();
//
//       Customer customer =customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Customer not found!"));
//
//       Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
//
//       if(!product.isInStock()){
//           throw new ResourceNotFoundException("Product is out of Stock!");
//       }
//       // Calculate discounted price
//       float originalPrice = product.getPrice();
//       float discountPercent = product.getDiscountPercent();
//       float discountAmount = (originalPrice*discountPercent)/100;
//       float discountedPrice = originalPrice - discountAmount;
//       float subTotal = discountedPrice*quantity;
//
//       if(discountedPrice < 0){
//        discountedPrice = product.getPrice();
//       }
//
//      Cart cart = cartRepository.findByCustomerId(customerId).orElseGet(()-> {
//          Cart newCart = new Cart();
//          newCart.setCustomer(customer);
//          newCart.setItems(new ArrayList<>());
//          return newCart;
//      });
//
//       Optional<CartItem> existingCartItem = cart.getItems().stream().filter(item ->item.getProduct().getId().equals(productId)).findFirst();
//
//       //If product is already in the cart update quatity and subtotal
//       if(existingCartItem.isPresent()){
//         CartItem cartItem =  existingCartItem.get();
//         cartItem.setQuantity(cartItem.getQuantity()+quantity);
//         cartItem.setDiscountedPrice(discountedPrice);
//         cartItem.setSubTotal(discountedPrice*cartItem.getQuantity());
//       }else{
//           CartItem cartItem = new CartItem();
//           cartItem.setProduct(product);
//           cartItem.setQuantity(quantity);
//           cartItem.setPrice(originalPrice);
//           cartItem.setDiscountedPrice(discountedPrice);
//           cartItem.setSubTotal(subTotal);
//           cartItem.setCart(cart);
//           cart.getItems().add(cartItem);
//           cartItemRepository.save(cartItem);
//       }
//
//       float totalCartPrice = (float) cart.getItems().stream().mapToDouble(CartItem::getSubTotal).sum();
//       cart.setTotalPrice(totalCartPrice);
//       Cart savedCart = cartRepository.save(cart);
//       return modelMapper.map(savedCart,CartResponseDTO.class);
//
//    }


    @Override
    public CartResponseDTO addToCart(AddToCartRequestDTO requestDTO) {
        Integer customerId = requestDTO.getCustomerId();
        Integer productId = requestDTO.getProductId();
        int quantity = requestDTO.getQuantity();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found!"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));

        if (!product.isInStock()) {
            throw new ResourceNotFoundException("Product is out of Stock!");
        }

        // Calculate discounted price
        float originalPrice = product.getPrice();
        float discountPercent = product.getDiscountPercent();
        float discountAmount = (originalPrice * discountPercent) / 100;
        float discountedPrice = originalPrice - discountAmount;
        float subTotal = discountedPrice * quantity;

        if (discountedPrice < 0) {
            discountedPrice = product.getPrice();
        }

        // Ensure Cart Exists
        Cart cart = cartRepository.findByCustomerId(customerId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setCustomer(customer);
            newCart.setItems(new ArrayList<>()); // Initialize items list
            return newCart;
        });

        // Save cart first (important for Hibernate)
        Cart savedCart = cartRepository.save(cart);

        // Check if the product is already in the cart
        Optional<CartItem> existingCartItem = savedCart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            //If the product is already in the cart , the quantity is increased
            cartItem.setDiscountedPrice(discountedPrice);
            cartItem.setSubTotal(discountedPrice * cartItem.getQuantity());
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(originalPrice);
            cartItem.setDiscountedPrice(discountedPrice);
            cartItem.setSubTotal(subTotal);
            cartItem.setCart(savedCart); // Use savedCart
            savedCart.getItems().add(cartItem);  //Adds the new item to the cart’s item list
            cartItemRepository.save(cartItem);
        }

        float totalCartPrice = (float) savedCart.getItems().stream().mapToDouble(CartItem::getSubTotal).sum();
        savedCart.setTotalPrice(totalCartPrice);

        // Save cart again after updating items
        Cart updatedCart = cartRepository.save(savedCart);

        return modelMapper.map(updatedCart, CartResponseDTO.class);
    }


    @Override
    public CartResponseDTO removeFromCart(Integer customerId, Integer productId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        // Find the CartItem to remove
        CartItem cartItemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart!"));

        // Remove the item
        cart.getItems().remove(cartItemToRemove);

        // Use `sum()` to calculate total price
        float totalCartPrice = (float) cart.getItems().stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();
        cart.setTotalPrice(totalCartPrice);

        // Save the updated cart
        Cart updatedCart = cartRepository.save(cart);
        return modelMapper.map(updatedCart, CartResponseDTO.class);
    }

    @Override
    public CartResponseDTO getCartByCustomer(Integer customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        // Convert Cart entity to CartResponseDTO
        return modelMapper.map(cart, CartResponseDTO.class);
    }
    @Override
    public void clearCart(Integer customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        Iterator<CartItem> iterator = cart.getItems().iterator();
        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();
            iterator.remove(); // Remove from list
            cartItemRepository.delete(cartItem); // Delete from DB
        }
        // Clear the cart items list
        cart.getItems().clear();

        // Set total price to 0
        cart.setTotalPrice(0.0f);

        // Save the cleared cart
        cartRepository.save(cart);
    }
}
