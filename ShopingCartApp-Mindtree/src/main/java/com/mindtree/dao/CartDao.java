package com.mindtree.dao;

import com.mindtree.dto.UserCart;
import com.mindtree.exception.DaoException;

public interface CartDao {
	
	public String addProductToCart(int userId, int prodId) throws DaoException;
	public String removeAllProductFromCart(int userId) throws DaoException;
	public String removeProductFromCart(int userId, int prodId) throws DaoException;
	public UserCart userCart(int userId) throws DaoException;
	public  String changeQuantityOfProductByNumber(int quantityOfProduct, int productId, int userId) throws DaoException;
	public void evictCache();
}
