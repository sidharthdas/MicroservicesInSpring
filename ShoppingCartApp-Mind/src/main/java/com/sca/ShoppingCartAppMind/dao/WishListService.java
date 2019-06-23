package com.sca.ShoppingCartAppMind.dao;

import java.util.List;

import com.sca.ShoppingCartAppMind.exception.DaoException;



public interface WishListService {
	
	public String addToWishList(Long userId,int productId) throws DaoException;
	public String removeProductFromWishList(Long userId, int productId) throws DaoException;
	public String removeAllProductFromWishList(Long userId) throws DaoException;
	public List<List> myWishList(Long userId) throws DaoException;
 
}
