package com.sca.ShoppingCartAppMind.dao;

import com.sca.ShoppingCartAppMind.model.User;

public interface UserService {
	
	public String addUser(User user);
	public User user(Long userId);

}
