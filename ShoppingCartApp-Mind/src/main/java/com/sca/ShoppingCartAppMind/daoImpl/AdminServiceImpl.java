package com.sca.ShoppingCartAppMind.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sca.ShoppingCartAppMind.dao.AdminService;
import com.sca.ShoppingCartAppMind.dto.UserSession;
import com.sca.ShoppingCartAppMind.exception.DaoException;
import com.sca.ShoppingCartAppMind.model.Admin;
import com.sca.ShoppingCartAppMind.model.Apparal;
import com.sca.ShoppingCartAppMind.model.Book;
import com.sca.ShoppingCartAppMind.model.Cart;
import com.sca.ShoppingCartAppMind.model.Product;
import com.sca.ShoppingCartAppMind.model.ProductQuantityCart;
import com.sca.ShoppingCartAppMind.model.User;
import com.sca.ShoppingCartAppMind.model.WishList;

@Repository
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public String addBook(Book book) throws DaoException {
		if (UserSession.adminUserName.equals("admin")) {
			List<Product> products = getSession().createQuery("FROM Product WHERE prodName = :prodName ")
					.setParameter("prodName", book.getProdName()).list();
			if (products.isEmpty()) {
				getSession().save(book);
				return "New item added.";
			}
			return "Item with the name is already in the Prodduct section.";
		}
		throw new DaoException("Need admin access.");
	}

	@Override
	public String addApparal(Apparal apparal) throws DaoException {
		if (UserSession.adminUserName.equals("admin")) {
			List<Product> products = getSession().createQuery("FROM Product WHERE prodName =  :prodName")
					.setParameter("prodName", apparal.getProdName()).list();
			if (products.isEmpty()) {
				getSession().save(apparal);
				return "New item added.";
			}
			return "Item with the name is already in the Prodduct section.";
		}
		throw new DaoException("Need admin access.");
	}

	@Override
	public String updateProductPrice(int productId, int price) throws DaoException {
		if (UserSession.adminUserName.equals("admin")) {

			List<Product> products = getSession().createQuery("FROM Product WHERE productId = :productId")
					.setParameter("productId", productId).list();
			if (products.get(0) == null) {
				return "no product is associated with id: " + productId;
			}
			products.get(0).setPrice(price);
			getSession().update(products.get(0));
			return "The price is updated for the product - " + products.get(0).getProdName();
		}
		throw new DaoException("Need admin access.");
	}

	@Override
	public String deleteProduct() throws DaoException {
		if (UserSession.adminUserName.equals("admin")) {

		}
		throw new DaoException("Need admin access.");
	}

	@Override
	public String adminLogin(Admin admin) throws DaoException {
		// TODO Auto-generated method stub
		List<Admin> admins = getSession().createQuery("FROM Admin where adminUserName = :adminUserName")
				.setParameter("adminUserName", admin.getAdminUserName()).list();
		if (admins.isEmpty()) {
			throw new DaoException("Not a admin.");
		} else if (admins.get(0).getAdminPassword().equals(admin.getAdminPassword())) {
			UserSession.adminUserName = admin.getAdminUserName();
			return "logged in sucessfully";
		}
		return "Password didnt match";
	}

	@Override
	public String addAdmin() {
		// TODO Auto-generated method stub
		Admin admin = new Admin();
		admin.setAdminUserName("admin");
		admin.setAdminPassword("admin123");
		getSession().save(admin);
		return "Added";
	}

	@Override
	public List<String> totalUsers() throws DaoException {
		if (UserSession.adminUserName.equals("admin")) {
			// TODO Auto-generated method stub
			List<String> userNames = new ArrayList<>();
			List<User> users = getSession().createQuery("FROM User").list();
			for (User u : users) {
				userNames.add(u.getUserName());
			}
			return userNames;
		}
		throw new DaoException("Need admin access.");
	}

	@Override
	public String updateBook(Book book) {
		// TODO Auto-generated method stub
		List<String> name = getSession().createQuery("SELECT prodName FROM Product WHERE prodName = :prodName")
				.setParameter("prodName", book.getProdName()).list();
		if (name.isEmpty()) {
			return "Product is not there with this name";
		}
		List<Integer> prodIds = getSession().createQuery("SELECT productId FROM Product WHERE prodName = :prodName")
				.setParameter("prodName", book.getProdName()).list();
		List<Book> books = getSession().createQuery("FROM Book WHERE productId = :productId")
				.setParameter("productId", prodIds.get(0)).list();
		books.get(0).setPrice(book.getPrice());
		books.get(0).setAuther(book.getAuther());
		books.get(0).setGenre(book.getGenre());
		books.get(0).setPublications(book.getPublications());
		getSession().update(books.get(0));
		return "Book is updated";
	}

	@Override
	public String updateApparal(Apparal apparal) {
		// TODO Auto-generated method stub
		List<String> names = getSession().createQuery("SELECT prodName FROM Product WHERE prodName = :prodName")
				.setParameter("prodName", apparal.getProdName()).list();
		if (names.isEmpty()) {
			return "Product is not there with this name";
		}
		List<Integer> prodIds = getSession().createQuery("SELECT productId FROM Product WHERE prodName = :prodName")
				.setParameter("prodName", apparal.getProdName()).list();
		List<Apparal> apparals = getSession().createQuery("FROM Apparal WHERE productId = :productId")
				.setParameter("productId", prodIds.get(0)).list();
		apparals.get(0).setPrice(apparal.getPrice());
		apparals.get(0).setBrand(apparal.getBrand());
		apparals.get(0).setDesign(apparal.getDesign());
		apparals.get(0).setType(apparal.getType());
		return "Apparal is updated";
	}

	@Override
	public String addNewAdmin(Admin admin) throws DaoException {
		// TODO Auto-generated method stub
		if (UserSession.adminUserName.equals("admin")) {
			List<Admin> admins = getSession().createQuery("FROM Admin WHERE adminUserName = :adminUserName")
					.setParameter("adminUserName", admin.getAdminUserName()).list();
			if (admins.isEmpty()) {
				getSession().save(admin);
				return "New Admin Added. ";
			}
			throw new DaoException("Admin already exist with this name. ");
		}

		throw new DaoException("Need Admin Access.");
	}

	@Override
	public String deleteUser(String userName) throws DaoException {
		// TODO Auto-generated method stub
		if (UserSession.adminUserName.equals("admin")) {
			List<User> users = getSession().createQuery("FROM User WHERE userName = :userName")
					.setParameter("userName", userName).list();
			if (users.isEmpty()) {
				return "No user found with the name " + userName;
			}
			User user = users.get(0);
			int cartId = users.get(0).getCart().getCartId();
			List<Cart> carts = getSession().createQuery("FROM Cart WHERE cartId = :cartId")
					.setParameter("cartId", user.getCart().getCartId()).list();

			List<ProductQuantityCart> productQuantityCarts = getSession()
					.createQuery("FROM ProductQuantityCart WHERE cartId = :cartId")
					.setParameter("cartId", user.getUserId()).list();

			List<WishList> wishLists = getSession().createQuery("FROM WishList WHERE id = :id")
					.setParameter("id", user.getCart().getCartId()).list();

			for (ProductQuantityCart p : productQuantityCarts) {
				getSession().delete(p);
			}

			users.get(0).setCart(null);
			users.get(0).setWishList(null);
			getSession().save(users.get(0));
			getSession().delete(users.get(0));
			getSession().delete(carts.get(0));
			getSession().delete(wishLists.get(0));

			return "The user is deleted";
		}
		throw new DaoException("Need Admin Access.");
	}

}
