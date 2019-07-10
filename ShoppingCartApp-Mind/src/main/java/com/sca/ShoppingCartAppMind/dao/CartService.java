package com.sca.ShoppingCartAppMind.dao;

import com.sca.ShoppingCartAppMind.dto.MyCart;
import com.sca.ShoppingCartAppMind.exception.DaoException;



public interface CartService {
	
	public String addProductToCart(Long userId, int prodId) throws DaoException;
	public String removeAllProductFromCart(Long userId) throws DaoException;
	public String removeProductFromCart(Long userId, int prodId) throws DaoException;
	public MyCart viewMyCart(Long userId) throws DaoException;
	public  String changeQuantityOfProductByNumber(int quantityOfProduct, int productId, Long userId) throws DaoException;
	public void setUserIdForAdminAccess(Long userId) throws DaoException;
}
