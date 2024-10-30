package com.luv2code.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "cart_item")
@Data
public class CartItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;

        private int quantity;

        public CartItem() {}

        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
}
