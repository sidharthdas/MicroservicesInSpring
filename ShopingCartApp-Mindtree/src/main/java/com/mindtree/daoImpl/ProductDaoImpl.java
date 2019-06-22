package com.mindtree.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.dao.ProductDao;
import com.mindtree.exception.DaoException;
import com.mindtree.model.Apparel;
import com.mindtree.model.Book;
import com.mindtree.model.Product;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<List> getAllProduct(int userId) throws DaoException {
		if (userId == 0) {
			throw new DaoException("Authenticatin required.");
		}
		List<List> products = new ArrayList<>();
		List<Book> productBook = getSession().createQuery("FROM Book").list();
		List<Apparel> productApparal = getSession().createQuery("FROM Apparel").list();
		products.add(productBook);
		products.add(productApparal);
		return products;
	}

	@Override
	public Product searchById(int userId, int productId) throws DaoException {
		// TODO Auto-generated method stub
		if (userId == 0) {
			throw new DaoException("Authenticatin required.");
		}
		// TODO Auto-generated method stub
		List<Long> count = getSession().createQuery("SELECT COUNT(*) FROM Product").list();
		if (count.get(0) < productId) {
			throw new DaoException("Product Not found with the product Id: " + productId);
		}
		List<Product> product = getSession().createQuery("FROM Product where productId = :productId").setParameter("productId", productId)
				.list();
		return product.get(0);
	}

	@Override
	public Product searchByName(int userId, String prodName) throws DaoException {
		if (userId == 0) {
			throw new DaoException("Authenticatin required.");
		}
		List<Product> product = getSession().createQuery("from Product where prodName = :prodName").setParameter("prodName", prodName)
				.list();
		if (product.isEmpty()) {
			throw new DaoException("Product " + prodName + " not found.");
		}
		return product.get(0);
	}

	@Override
	public List<Book> catagoryBook(int userId) throws DaoException {
		if (userId == 0) {
			throw new DaoException("Authenticatin required.");
		}
		List<Book> books = getSession().createQuery("FROM Book").list();
		return books;
	}

	@Override
	public List<Apparel> catagoryApparel(int userId) throws DaoException {
		if (userId == 0) {
			throw new DaoException("Authenticatin required.");
		}
		// TODO Auto-generated method stub
		List<Apparel> apparels = getSession().createQuery("FROM Apparel").list();
		return apparels;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void autoAddProduct() {
		// TODO Auto-generated method stub
		Book book = new Book();
		Apparel app = new Apparel();

		book.setProdName("C for Computer Science");
		book.setPrice(500);
		book.setAuther("Dennis Ritchie");
		book.setGenre("Programming");
		book.setPublications("unknown");

		app.setProdName("Pant");
		app.setDesign("Manish Malhotra");
		app.setBrand("Something");
		app.setPrice(1000);
		app.setType("Jeans");
		Book book1 = new Book();
		Book book2 = new Book();
		Apparel app1 = new Apparel();

		book1.setProdName("Harry Potter");
		book1.setPrice(600);
		book1.setAuther("J.K Rowling");
		book1.setGenre("Novel");
		book1.setPublications("unknown");
		
		book2.setProdName("Java");
		book2.setPrice(1600);
		book2.setAuther("Unknown");
		book2.setGenre("Study");
		book2.setPublications("unknown");

		app1.setProdName("T-Shirt");
		app1.setDesign("Levies Jeans");
		app1.setBrand("Levies");
		app1.setPrice(3999);
		app1.setType("Shirt");

		getSession().save(book);
		getSession().save(app);
		getSession().save(book1);
		getSession().save(app1);
		getSession().save(book2);

	}

}
