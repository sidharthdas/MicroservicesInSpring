package com.mindtree.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.dao.CartDao;
import com.mindtree.dto.ProductQuantity;
import com.mindtree.dto.UserCart;
import com.mindtree.dto.UserSession;
import com.mindtree.exception.DaoException;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/api/cart/")
public class CartController {

	@Autowired
	private CartDao cartDao;

	@RequestMapping(value = "test-cart", method = RequestMethod.GET)
	public String test() {
		return "CartController is working";
	}
	
	private static final Logger log = Logger.getLogger(CartController.class); 

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResponseEntity<String> addProductToCart(@RequestBody String prodId) {
		try {
			return new ResponseEntity<String>(cartDao.addProductToCart(UserSession.userId, Integer.parseInt(prodId)), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
	}
	
	@RequestMapping(value = "mycart", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeAllProductFromCart() {
		try {
			return new ResponseEntity<String>(cartDao.removeAllProductFromCart(UserSession.userId), HttpStatus.ACCEPTED);
		} catch (DaoException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	  
	@RequestMapping(value = "mycart/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeProductFromCart(@PathVariable("productId") int productId){
		System.out.println("the product id from url is "+productId);
		try {
			return new ResponseEntity<String>(cartDao.removeProductFromCart(UserSession.userId, productId), HttpStatus.ACCEPTED); 
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}  
	
	@RequestMapping(value = "mycart", method = RequestMethod.GET)
	public ResponseEntity<UserCart> userCart(){
		try {
			return new ResponseEntity<UserCart>(cartDao.userCart(UserSession.userId), HttpStatus.FOUND);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "mycart", method = RequestMethod.PUT) 
	public String changeQuantityOfProductByNumber(@RequestBody ProductQuantity productQuantity) {
		try {
			return cartDao.changeQuantityOfProductByNumber(productQuantity.getQuantity(), productQuantity.getProductId(), UserSession.userId);
		} catch (DaoException e) {  
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			log.error(e.getMessage());
		}
		return null; 
	}
} 
