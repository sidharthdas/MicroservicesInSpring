package com.sca.ShoppingCartAppMind.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@Column(name = "user_id")
	private Long userId;
	//@Column(name = "user_name")
	private String userName;
	//@Column(name  = "password")
	private String password;
	@OneToOne
	private Cart cart;
	@OneToOne
	private WishList wishList;
	
	
	public WishList getWishList() {
		return wishList;
	}
	public void setWishList(WishList wishList) {
		this.wishList = wishList;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
