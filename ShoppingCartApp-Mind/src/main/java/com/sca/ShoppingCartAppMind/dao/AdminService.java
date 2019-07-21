package com.sca.ShoppingCartAppMind.dao;

import java.util.List;

import com.sca.ShoppingCartAppMind.exception.DaoException;
import com.sca.ShoppingCartAppMind.model.Admin;
import com.sca.ShoppingCartAppMind.model.Apparal;
import com.sca.ShoppingCartAppMind.model.Book;


public interface AdminService {
	
	public String adminLogin(Admin admin) throws DaoException;
	
	public String addBook(Book book)  throws DaoException;
	public String addApparal(Apparal apparal) throws DaoException;
	public String updateProductPrice(int productId, int price) throws DaoException;
	public String deleteProduct() throws DaoException;
	public String addAdmin();
	public String addNewAdmin(Admin admin) throws DaoException;
	public List<String> totalUsers() throws DaoException;
	public String updateBook(Book book);
	public String updateApparal(Apparal apparal);
	public String deleteUser(String userName) throws DaoException;
	
	
}
