package com.mindtree.dto;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.dto.ProductInCart;

public class UserCart {
	
	private List<ProductInCart> productInCart = new ArrayList<>();

	public List<ProductInCart> getProductInCart() {
		return productInCart;
	}

	public void setProductInCart(List<ProductInCart> productInCart) {
		this.productInCart = productInCart;
	}
	
	

}
