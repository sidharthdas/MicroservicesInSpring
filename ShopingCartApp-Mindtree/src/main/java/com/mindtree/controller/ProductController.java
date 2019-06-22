package com.mindtree.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.dao.ProductDao;
import com.mindtree.dto.UserSession;
import com.mindtree.exception.DaoException;
import com.mindtree.model.Product;

@RestController
@RequestMapping("/api/product/")
public class ProductController {

	@Autowired
	private ProductDao productDao;

	@RequestMapping(value = "test-product")

	public String test() {
		return "ProductController is working. . .";
	}
	private static final Logger log = Logger.getLogger(ProductController.class); 
	
	@RequestMapping(value = "all-products", method = RequestMethod.GET)
	public ResponseEntity<List<List>> getAllProduct() {
		try {
			return new ResponseEntity<List<List>>(productDao.getAllProduct(UserSession.userId), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "id/{productId}", method = RequestMethod.GET)
	public ResponseEntity<Product> getById(@PathVariable("productId") int productId) {
		try {
			return new ResponseEntity<Product>(productDao.searchById(UserSession.userId, productId), HttpStatus.FOUND);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			log.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	}
	
	@RequestMapping(value = "name/{productName}", method = RequestMethod.GET)
	public ResponseEntity<Product> getByName(@PathVariable("productName") String productName) {
		try {
			//System.out.println(prodName);
			return new ResponseEntity<Product>(productDao.searchByName(UserSession.userId, productName),
					HttpStatus.FOUND); 
		} catch (DaoException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "category/{catName}", method = RequestMethod.GET)     
	public ResponseEntity<List> getByCatagory(@PathVariable("catName") String catName) {
		if (catName.equals("Books")) { 
			try {
				return new ResponseEntity<List>(productDao.catagoryBook(UserSession.userId), HttpStatus.FOUND);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				log.error(e.getMessage());
			} 
		} else if (catName.equals("Apparels")) {
			try {
				return new ResponseEntity<List>(productDao.catagoryApparel(UserSession.userId), HttpStatus.FOUND);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				log.error(e.getMessage());
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "add-product", method = RequestMethod.POST)    
	public void addProducts() {

		productDao.autoAddProduct(); 
	}  
	   
	
}