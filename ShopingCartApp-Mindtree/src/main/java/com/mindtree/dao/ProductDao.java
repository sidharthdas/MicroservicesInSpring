package com.mindtree.dao;

import java.util.List;

import com.mindtree.exception.DaoException;
import com.mindtree.model.Apparel;
import com.mindtree.model.Book;
import com.mindtree.model.Product;


public interface ProductDao {
	
	public List<List> getAllProduct(int userId) throws DaoException;
	public Product searchById(int userId, int productId) throws DaoException;
	public Product searchByName(int userId, String prodName) throws DaoException;
	public List<Book> catagoryBook(int userId) throws DaoException;
	public List<Apparel> catagoryApparel(int userId) throws DaoException;
	public void autoAddProduct();

}
