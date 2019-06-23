 package com.sca.ShoppingCartAppMind.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {
	
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", prodName=" + prodName + ", price=" + price + ", carts=" + carts
				+ "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String prodName;
	private float price;
	@ManyToMany(mappedBy = "products")
	private Collection<Cart> carts = new ArrayList<>();

	public int getProductId() {
		return productId; 
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
