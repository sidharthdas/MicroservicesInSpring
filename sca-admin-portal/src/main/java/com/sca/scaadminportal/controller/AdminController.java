package com.sca.scaadminportal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.sca.scaadminportal.dao.AdminDao;
import com.sca.scaadminportal.dto.AdminDto;
import com.sca.scaadminportal.dto.Apparal;
import com.sca.scaadminportal.dto.Book;
import com.sca.scaadminportal.dto.Product;
import com.sca.scaadminportal.exception.DaoException;
import com.sca.scaadminportal.model.Admin;

@RestController
@RequestMapping("/api/admin")
public class AdminController {	
	
	
	@Autowired
	private AdminDao adminDao;

	@Autowired
	private RestTemplate getRestTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;

	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test() {
		return "Admin controller is working";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addAdmin(@RequestBody Admin admin) {
		return adminDao.addAdmin(admin);
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<String> adminLogin(@RequestBody Admin admin) {
		try {
			return new ResponseEntity<String>(adminDao.adminLogin(admin), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@RequestMapping(value = "testProduct", method = RequestMethod.GET)
	public Book product() {

		Book book = getRestTemplate.getForObject("http://SHOPPING-CART-APP/api/product/id/1", Book.class);
		return book;
	}

	@RequestMapping(value = "add-product-client", method = RequestMethod.POST)
	public String product(@RequestBody Book book) {

		String result = getRestTemplate.postForObject("http://SHOPPING-CART-APP/api/admin/add-book", book,
				String.class);
		return result;
	}

	@RequestMapping(value = "update-book", method = RequestMethod.PUT)
	public String updateBook(@RequestBody Book book) {
		String uri = "http://localhost:9090/api/admin/update-book";
		getRestTemplate.put(uri, book);
		return "Updated";

	}

	@RequestMapping(value = "update-apparal", method = RequestMethod.PUT)
	public String updateApparal(@RequestBody Apparal apparal) {
		String uri = "http://SHOPPING-CART-APP/api/admin/update-apparal";
		getRestTemplate.put(uri, apparal);
		return "Apparal updated";

	}

	@RequestMapping(value = "test123", method = RequestMethod.GET)
	public String test123() {
		String name = getRestTemplate.getForObject("http://SHOPPING-CART-APP/api/admin/test", String.class);
		return name;
	}

	@RequestMapping(value = "/login-admin", method = RequestMethod.POST)
	public String adminLogin1(@RequestBody AdminDto admin) {
		String login = getRestTemplate.postForObject("http://SHOPPING-CART-APP/api/admin/login", admin, String.class);
		return login;
	}

	@RequestMapping(value = "/add-new-admin", method = RequestMethod.POST)
	public String addNewAdmin(@RequestBody AdminDto admin) {
		return getRestTemplate.postForObject("http://SHOPPING-CART-APP/api/admin/add-new-admin", admin, String.class);
	}

	@RequestMapping(value = "delete-user", method = RequestMethod.DELETE)
	public String deleteUser(@RequestParam(value = "userName", required = true) String userName) {
		 Map<String, String> params = new HashMap<String, String>();
		    params.put("userName", userName);
		getRestTemplate.delete("http://SHOPPING-CART-APP/api/admin/delete-user?userName="+userName);
		return "Deleted";
	}
	
	@RequestMapping(value= "all-products", method = RequestMethod.GET)
	public Object allProduct(){
		Object allProducts = getRestTemplate.getForObject("http://SHOPPING-CART-APP/api/product/all-products", Object.class);
		return allProducts;
	}
	
	@RequestMapping(value = "category", method = RequestMethod.GET)
	public Object getByCat(@RequestParam(value= "catName", required = true) String catName) {
		Object value = getRestTemplate.getForObject("http://SHOPPING-CART-APP/api/product/category/"+catName,Object.class);
		
		return value;
	}
	
	@RequestMapping(value = "product", method = RequestMethod.GET)
	public Product getById(@RequestParam(value = "id", required = true) int id) {
		if(id%2 ==1) {
		Book product = getRestTemplate.getForObject("http://SHOPPING-CART-APP/api/product/id/"+id, Book.class);
		return product;
		}
		Apparal product = getRestTemplate.getForObject("http://SHOPPING-CART-APP/api/product/id/"+id,  Apparal.class);
		return product;
	}
	
	/*
	 * public String deleteUserUsingWebClient(String userName) {
	 * webClientBuilder.build() .delete()
	 * .uri("http://SHOPPING-CART-APP/api/admin/delete-user?userName="+userName)
	 * .retrieve() .bodyToMono(String.class) .block(); return null; }
	 */

}
