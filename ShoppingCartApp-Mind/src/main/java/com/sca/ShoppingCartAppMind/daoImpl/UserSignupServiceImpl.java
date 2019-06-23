package com.sca.ShoppingCartAppMind.daoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.sca.ShoppingCartAppMind.dao.UserSignupService;
import com.sca.ShoppingCartAppMind.dto.UserDetails;
import com.sca.ShoppingCartAppMind.exception.DaoException;
import com.sca.ShoppingCartAppMind.model.Cart;
import com.sca.ShoppingCartAppMind.model.User;
import com.sca.ShoppingCartAppMind.model.WishList;



@Repository
@Transactional
public class UserSignupServiceImpl implements UserSignupService {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession(); 
	}

	public Boolean checkUserName(String userName) {
		String userNameFromDB = getSession().createQuery("SELECT userName FROM User where userName = :userName")
				.setParameter("userName", userName).list().toString();
		if (userNameFromDB.equals("[]")) {
			return false;
		}
		return true;
	}


	public Boolean confirmPassword(String password, String confirmPassword) {
		if (password.equals(confirmPassword)) {
			return true;
		}
		return false;
	}

	@Override
	public Long addUser(UserDetails userDetail) throws DaoException {
		if (checkUserName(userDetail.getUserName()) == false) {
			Boolean confirmPass = confirmPassword(userDetail.getPassword(), userDetail.getConfirmPassword());
			if (confirmPass) {

				User user = new User();
				Cart cart = new Cart();
				WishList wishList = new WishList();
				user.setUserName(userDetail.getUserName());
				user.setPassword(userDetail.getPassword());
				user.setCart(cart);
				user.setWishList(wishList);
				//cart.getProducts().add(book);
				getSession().save(user);
				getSession().save(cart);
				getSession().save(wishList);
				
				List<Long>userId = getSession().createQuery("SELECT userId FROM User WHERE userName = :userName").setParameter("userName", user.getUserName()).list();
				
				return userId.get(0); 
			}

			throw new DaoException("Password didnt match.");
		}
		throw new DaoException("UserName Already exist.");
	}

	@Override
	public Long loginUser(User user) throws DaoException {
		if(checkUserName(user.getUserName())) {
			String passwordFromDB = getSession().createQuery("SELECT password FROM User WHERE userName = :userName").setParameter("userName", user.getUserName()).list().toString();
			String password = passwordFromDB.substring(1, passwordFromDB.length()-1);
			if(password.equals(user.getPassword())) {
				List<Long> userId = getSession().createQuery("SELECT userId FROM User WHERE userName = :userName").setParameter("userName", user.getUserName()).list();
				return userId.get(0);
			}
			else {
				throw new DaoException("Passowrd Didnt match");
			}
		}
		//return 0;
		throw new DaoException("User not found");
		
	}
	
}
