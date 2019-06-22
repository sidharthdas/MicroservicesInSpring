package com.mindtree.daoImpl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.dao.UserDao;

import com.mindtree.dto.UserSession;
import com.mindtree.exception.DaoException;
import com.mindtree.model.Cart;
import com.mindtree.model.User;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public int randomNumber() {
		int max = 9999;
		int min = 1000;
		int range = max - min + 1;

		int rand = (int) (Math.random() * range) + min;
		return rand;
	}

	public Boolean checkUserName(String userName) {

		List<User> users = getSession().createQuery("FROM User WHERE userName = :userName").setParameter("userName", userName).list();
		return users.isEmpty();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int addUser(User user) throws DaoException {
		// TODO Auto-generated method stub
		if (checkUserName(user.getUserName())) {
			Cart cart = new Cart();
			user.setCart(cart);
			getSession().save(user);
			getSession().save(cart);
			List<Integer> ids = getSession().createQuery("SELECT userId FROM User WHERE userName = :userName")
					.setParameter("userName", user.getUserName()).list();
			UserSession.userId = ids.get(0);
			return ids.get(0);
		}
		throw new DaoException("UserName Already Exist.");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int loginUser(User user) throws DaoException {
		// TODO Auto-generated method stub
		if (checkUserName(user.getUserName()) == false) {
			List<User> users = getSession().createQuery("FROM User WHERE userName = :userName")
					.setParameter("userName", user.getUserName()).list();
			if (users.get(0).getPassword().equals(user.getPassword())) {
				List<Integer> ids = getSession().createQuery("SELECT userId FROM User WHERE userName = :userName")
						.setParameter("userName", user.getUserName()).list();
				UserSession.userId = ids.get(0);
				return ids.get(0);
			}
			throw new DaoException("Password didint match.");
		}
		throw new DaoException("Unregistered user.");
	}

}
