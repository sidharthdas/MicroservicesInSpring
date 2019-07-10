package com.sca.scaadminportal.dao;


import com.sca.scaadminportal.dto.Apparal;
import com.sca.scaadminportal.dto.Book;
import com.sca.scaadminportal.exception.DaoException;
import com.sca.scaadminportal.model.Admin;

public interface AdminDao {
	
	public String adminLogin(Admin admin) throws DaoException;
	public String addAdmin(Admin admin);
	public String addBook(Book book)  throws DaoException;
	public String addApparal(Apparal apparal) throws DaoException;
	//public 

}
