package com.sca.ShoppingCartAppMind.dto;

import java.util.ArrayList;
import java.util.List;

public class MyCart {

	private List<ProductInCart> productInCart = new ArrayList<>();
	private double totalAmount;

	public List<ProductInCart> getProductInCart() {
		return productInCart;
	}

	public void setProductInCart(List<ProductInCart> productInCart) {
		this.productInCart = productInCart;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
