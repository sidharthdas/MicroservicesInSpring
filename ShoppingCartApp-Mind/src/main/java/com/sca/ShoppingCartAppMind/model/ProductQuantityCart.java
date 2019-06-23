package com.sca.ShoppingCartAppMind.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductQuantityCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Long cartId;
	private int productId;
	private int prodQuantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long userId) {
		this.cartId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProdQuantity() {
		return prodQuantity;
	}

	public void setProdQuantity(int prodQuantity) {
		this.prodQuantity = prodQuantity;
	}

}
