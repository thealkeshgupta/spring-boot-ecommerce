package com.ecommerce.app_server.payload;

public class CartItemDTO {
    private Long cartItemId;
    private CartDTO cart;
    private ProductDTO product;
    private Integer quantity;
    private Double discount;
    private Double productPrice;
}
