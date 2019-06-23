package com.sca.ShoppingCartAppMind.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sca.ShoppingCartAppMind.dao.AdminService;
import com.sca.ShoppingCartAppMind.dto.UserSession;
import com.sca.ShoppingCartAppMind.exception.DaoException;
import com.sca.ShoppingCartAppMind.model.Admin;
import com.sca.ShoppingCartAppMind.model.Apparal;
import com.sca.ShoppingCartAppMind.model.Book;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test() {
		return "Admin controller is working";
	}

	@RequestMapping(value = "test123", method = RequestMethod.GET)
	public String testRequestParam(@RequestParam(value = "name", required = true) String name) {
		return "RequestParam is working. User name " + name;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addAdmin() {
		return adminService.addAdmin();
	}

	@RequestMapping(value = "/add-new-admin", method = RequestMethod.POST)
	public ResponseEntity<String> addNewAdmin(@RequestBody Admin admin) {
		try {
			return new ResponseEntity<String>(adminService.addNewAdmin(admin), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<String> adminLogin(@RequestBody Admin admin) {
		try {
			return new ResponseEntity<String>(adminService.adminLogin(admin), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@RequestMapping(value = "add-book", method = RequestMethod.POST)
	public ResponseEntity<String> addBook(@RequestBody Book book) {
		try {
			return new ResponseEntity<String>(adminService.addBook(book), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "add-apparal", method = RequestMethod.POST)
	public ResponseEntity<String> addApparal(@RequestBody Apparal apparal) {
		try {
			return new ResponseEntity<String>(adminService.addApparal(apparal), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "product/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateProductPrice(@PathVariable("productId") int productId, @RequestBody int price) {
		try {
			return new ResponseEntity<String>(adminService.updateProductPrice(productId, price), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public String adminLogout() {
		UserSession.adminUserName = null;
		return "Admin is loggedout Sucessfully";
	}

	@RequestMapping(value = "all-users", method = RequestMethod.GET)
	public ResponseEntity<List<String>> allUsers() {
		try {
			return new ResponseEntity<List<String>>(adminService.totalUsers(), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "update-book", method = RequestMethod.PUT)
	public String updateBook(@RequestBody Book book) {
		return adminService.updateBook(book);
	}

	@RequestMapping(value = "update-apparal", method = RequestMethod.PUT)
	public String updateApparal(@RequestBody Apparal apparal) {
		return adminService.updateApparal(apparal);
	}

	@RequestMapping(value = "delete-user", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@RequestParam(value = "userName", required = true) String userName) {
		try {
			return new ResponseEntity<String>(adminService.deleteUser(userName), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

}
