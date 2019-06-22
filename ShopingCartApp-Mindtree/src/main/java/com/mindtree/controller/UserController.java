package com.mindtree.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.dao.UserDao;
import com.mindtree.dto.ForgetPass;
import com.mindtree.dto.UserSession;
import com.mindtree.exception.DaoException;
import com.mindtree.model.User;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserDao userDao;
	
	private static final Logger log = Logger.getLogger(UserController.class); 

	@RequestMapping(value = "test-user", method = RequestMethod.GET)
	public String testUserController() {
		return "UserController is working";
	}

	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public ResponseEntity<Integer> addUser(@RequestBody User user) {
		try {
			return new ResponseEntity<Integer>(userDao.addUser(user), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE); 
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<Integer> loginUser(@RequestBody User user) {
		try {
			return new ResponseEntity<Integer>(userDao.loginUser(user), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public void userLogout(){
		UserSession.userId = 0;
		System.out.println("logged out sucessfully.");
		System.out.println(UserSession.userId);
	}

}