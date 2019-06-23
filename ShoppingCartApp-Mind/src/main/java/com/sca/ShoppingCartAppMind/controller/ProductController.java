package com.sca.ShoppingCartAppMind.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sca.ShoppingCartAppMind.dao.ProductService;
import com.sca.ShoppingCartAppMind.dto.UserSession;
import com.sca.ShoppingCartAppMind.exception.DaoException;
import com.sca.ShoppingCartAppMind.model.Product;

@RestController
@RequestMapping("api/product/")
public class ProductController {

	@Autowired
	private ProductService productService;
	


	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test() {
		return "Product Controller is working";
	}

	@RequestMapping(value = "all-products", method = RequestMethod.GET)
	public ResponseEntity<List<List>> getAllProduct() {
		try {
			return new ResponseEntity<List<List>>(productService.getAllProduct(UserSession.adminUserName, UserSession.userId), HttpStatus.FOUND);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());   

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "id/{productId}", method = RequestMethod.GET)
	public ResponseEntity<Product> getById(@PathVariable("productId") int productId) {
		try {
			return new ResponseEntity<Product>(productService.searchById(UserSession.userId, productId),
					HttpStatus.FOUND);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "product-name/{prodName}", method = RequestMethod.GET)      
	public ResponseEntity<Product> getByProdName(@PathVariable("prodName") String prodName) {
		try {
			System.out.println(prodName);
			return new ResponseEntity<Product>(productService.searchByName(UserSession.userId, prodName),
					HttpStatus.FOUND); 
		} catch (DaoException e) {
			System.out.println(e.getMessage());
			
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	
	@RequestMapping(value = "category/{catName}", method = RequestMethod.GET)     
	public ResponseEntity<List> getByCatagory(@PathVariable("catName") String catName) {
		if (catName.equals("Books")) {
			try {
				return new ResponseEntity<List>(productService.catagoryBook(UserSession.userId), HttpStatus.FOUND);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				
			} 
		} else if (catName.equals("Apparals")) {
			try {
				return new ResponseEntity<List>(productService.catagoryApparal(UserSession.userId), HttpStatus.FOUND);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "add-product-test", method = RequestMethod.POST)
	public void autoAddProducts() {

		productService.autoAddProduct();
	} 

}
