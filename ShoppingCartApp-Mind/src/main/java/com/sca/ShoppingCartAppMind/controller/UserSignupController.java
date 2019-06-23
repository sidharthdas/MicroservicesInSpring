package com.sca.ShoppingCartAppMind.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sca.ShoppingCartAppMind.dao.UserSignupService;
import com.sca.ShoppingCartAppMind.dto.UserDetails;
import com.sca.ShoppingCartAppMind.dto.UserSession;
import com.sca.ShoppingCartAppMind.exception.DaoException;
import com.sca.ShoppingCartAppMind.model.User;

@RestController
@RequestMapping("/api/user/")
public class UserSignupController { 
	
	@Autowired
	private UserSignupService userSignupService;
	

	@RequestMapping(value = "test", method = RequestMethod.GET)  
	public String test() {
		return "Signup controller is working";
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public ResponseEntity<Long> addUser(@RequestBody UserDetails userDetail) {
		try {
			UserSession.userId = userSignupService.addUser(userDetail);
			System.out.println(UserSession.userId);
			return new ResponseEntity<Long>(UserSession.userId, HttpStatus.ACCEPTED);
		} catch (DaoException e) {
			System.out.println(e.getMessage());
			
		}
		return new ResponseEntity<>(HttpStatus.OK);   
	}  
	 
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<Long> userLogin(@RequestBody User user) {
		try {
			UserSession.userId = userSignupService.loginUser(user);
			System.out.println(UserSession.userId);
			return new ResponseEntity<Long>(UserSession.userId, HttpStatus.ACCEPTED);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage()+"");
			
		}
		return new ResponseEntity<>(HttpStatus.OK); 
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public void logout() {
		UserSession.userId = null; 
		System.out.println(UserSession.userId); 
	}
	
}
