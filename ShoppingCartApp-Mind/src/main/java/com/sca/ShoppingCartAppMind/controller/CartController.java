package com.sca.ShoppingCartAppMind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sca.ShoppingCartAppMind.dao.CartService;
import com.sca.ShoppingCartAppMind.dto.MyCart;
import com.sca.ShoppingCartAppMind.dto.ProductAndQuantity;
import com.sca.ShoppingCartAppMind.dto.UserSession;
import com.sca.ShoppingCartAppMind.exception.DaoException;


@RestController
@RequestMapping("/api/cart/")
public class CartController {

	@Autowired
	private CartService cartService;
 
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test() {
		return "Cart controller is working.";
	} 


	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResponseEntity<String> addProductToCart(@RequestBody String prodId) {
		try {
			System.out.println(UserSession.userId);
			System.out.println(prodId);
			return new ResponseEntity<String>(cartService.addProductToCart(UserSession.userId, Integer.parseInt(prodId)),
					HttpStatus.ACCEPTED);
		} catch (DaoException e) {
			// TODO Auto-generated catch block   
			System.out.println(e.getMessage());

		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "remove", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeAllItemFromCart() {
		try {
			return new ResponseEntity<String>(cartService.removeAllProductFromCart(UserSession.userId), HttpStatus.ACCEPTED);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage()+"");

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "remove/{productId}", method = RequestMethod.DELETE) 
	public ResponseEntity<String> removeItemFromCart(@PathVariable("productId") String prodId) {
		try {
			return new ResponseEntity<String>(cartService.removeProductFromCart(UserSession.userId, Integer.parseInt(prodId)), HttpStatus.ACCEPTED);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "mycart", method = RequestMethod.GET)
	public ResponseEntity<MyCart> myCart() {
		try {
			return new ResponseEntity<MyCart>(cartService.viewMyCart(UserSession.userId), HttpStatus.FOUND);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} 
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "mycart", method = RequestMethod.PUT)
	public String changeQuantityOfProductByNumber(@RequestBody ProductAndQuantity productAndQuantity) {
		try {
			return cartService.changeQuantityOfProductByNumber(productAndQuantity.getQuantity(), productAndQuantity.getProductId(), UserSession.userId);
		} catch (DaoException e) {  
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	
	@RequestMapping(value = "adminAccess/{id}", method = RequestMethod.POST)
	public void setUserIdForAdminAccess(@PathVariable("id") Long id) {
		try {
			cartService.setUserIdForAdminAccess(id);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
