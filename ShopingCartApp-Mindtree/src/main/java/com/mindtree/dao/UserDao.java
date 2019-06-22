package com.mindtree.dao;

import com.mindtree.dto.ForgetPass;
import com.mindtree.exception.DaoException;
import com.mindtree.model.User;

public interface UserDao {
	
	public int addUser(User user) throws DaoException;
	public int loginUser(User user) throws DaoException;


}
