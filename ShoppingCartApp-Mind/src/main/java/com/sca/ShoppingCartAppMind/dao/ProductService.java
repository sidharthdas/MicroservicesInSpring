package com.sca.ShoppingCartAppMind.dao;

import java.util.List;

import com.sca.ShoppingCartAppMind.exception.DaoException;
import com.sca.ShoppingCartAppMind.model.Apparal;
import com.sca.ShoppingCartAppMind.model.Book;
import com.sca.ShoppingCartAppMind.model.Product;



public interface ProductService {
	
	public List<List> getAllProduct(String adminName,Long userId) throws DaoException;
	public Product searchById(Long userId, int productId) throws DaoException;
	public Product searchByName(Long userId, String prodName) throws DaoException;
	public List<Book> catagoryBook(Long userId) throws DaoException;
	public List<Apparal> catagoryApparal(Long userId) throws DaoException;
	public void autoAddProduct();
}
