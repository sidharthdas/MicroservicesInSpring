package com.sca.ShoppingCartAppMind.dao;

import com.sca.ShoppingCartAppMind.dto.UserDetails;
import com.sca.ShoppingCartAppMind.exception.DaoException;
import com.sca.ShoppingCartAppMind.model.User;


public interface UserSignupService {
	
	public Long addUser(UserDetails userDetail) throws DaoException;
	public Long loginUser(User user) throws DaoException;

}
