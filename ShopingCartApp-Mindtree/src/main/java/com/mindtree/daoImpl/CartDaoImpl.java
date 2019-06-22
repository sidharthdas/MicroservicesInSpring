package com.mindtree.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.dao.CartDao;
import com.mindtree.dto.ProductInCart;
import com.mindtree.dto.UserCart;
import com.mindtree.exception.DaoException;
import com.mindtree.model.Apparel;
import com.mindtree.model.Book;
import com.mindtree.model.Product;
import com.mindtree.model.ProductsInCart;
import com.mindtree.model.User;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CartDaoImpl implements CartDao {    

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Boolean checkProductInCart(int userId, int prodId) {
		List<User> user = getSession().createQuery("FROM User WHERE userId = :userId ").setParameter("userId", userId)
				.list();
		List<Product> product = getSession().createQuery("FROM Product where productId = :productId")
				.setParameter("productId", prodId).list();
		System.out.println(user.get(0).getCart().getProducts().isEmpty());
		Boolean checkCart = user.get(0).getCart().getProducts().contains(product.get(0));
		return checkCart;

	}

	public String addInproductQuantityCart(int userId, int prodId) {
		List<ProductsInCart> productQuantityCart = getSession()
				.createQuery("FROM ProductsInCart WHERE cartId = :cartId AND productId = :productId")
				.setParameter("cartId", userId).setParameter("productId", prodId).list();
		int count = productQuantityCart.get(0).getProductQuantity() + 1;
		productQuantityCart.get(0).setProductQuantity(count);
		getSession().save(productQuantityCart.get(0));
		return "added sucesssfully";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "cart", key = "#result.id")
	public String addProductToCart(int userId, int prodId) throws DaoException {
		if (userId == 0) {
			throw new DaoException("Authentication Required.");
		}
		if (checkProductInCart(userId, prodId)) {
			System.out.println("in daoImp 7");
			return addInproductQuantityCart(userId, prodId);
		}
		List<User> user = getSession().createQuery("FROM User where cart_cartId = :cartId")
				.setParameter("cartId", userId).list();
		List<Book> book = getSession().createQuery("FROM Book where productId = :productId")
				.setParameter("productId", prodId).list();
		if (book.isEmpty()) {
			List<Apparel> apparal = getSession().createQuery("FROM Apparel where productId = :productId")
					.setParameter("productId", prodId).list();
			user.get(0).getCart().getProducts().add(apparal.get(0));
			getSession().update(user.get(0));
			ProductsInCart pqc = new ProductsInCart();
			pqc.setCartId(userId);
			pqc.setProductId(prodId);
			pqc.setProductQuantity(1);
			getSession().save(pqc);
			return "Added sucessfully.";
		}
		user.get(0).getCart().getProducts().add(book.get(0));
		getSession().save(user.get(0));
		ProductsInCart pqc = new ProductsInCart();
		pqc.setCartId(userId);
		pqc.setProductId(prodId);
		pqc.setProductQuantity(1);
		getSession().save(pqc);
		return "Added sucessfully.";

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value = "cart", key = "#userId")
	public String removeAllProductFromCart(int userId) throws DaoException {
		if (userId == 0) {
			throw new DaoException("Authentication Required");
		}
		List<ProductsInCart> productQuantityCart = getSession()
				.createQuery("FROM ProductsInCart WHERE cartId = :cartId").setParameter("cartId", userId).list();
		for (ProductsInCart p : productQuantityCart) {
			getSession().delete(p);
		}
		List<User> user = getSession().createQuery("FROM User WHERE userId = :userId").setParameter("userId", userId)
				.list();
		user.get(0).getCart().setProducts(null);
		getSession().update(user.get(0));
		getSession().save(user.get(0));
		return "removed";

	}
       
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "cart", key = "#userId")
	public String changeQuantityOfProductByNumber(int quantityOfProduct, int productId, int userId)
			throws DaoException {
		// TODO Auto-generated method stub
		if (userId == 0) {
			throw new DaoException("Authentication required");
		}
		if (quantityOfProduct == 0) {
			List<User> user = getSession().createQuery("FROM User WHERE userId = :userId")
					.setParameter("userId", userId).list();
			List<Product> product = getSession().createQuery("FROM Product WHERE productId = :productId")
					.setParameter("productId", productId).list();
			user.get(0).getCart().getProducts().remove(product.get(0));
			getSession().createQuery("DELETE FROM ProductsInCart where cartId = :cartId AND productId = :productId")
					.setParameter("cartId", userId).setParameter("productId", productId).executeUpdate();
			return "Product removed sucessfully";
		}
		System.out.println(userId);
		System.out.println(productId);
		System.out.println(quantityOfProduct);
		List<ProductsInCart> productrQuantityCart = getSession()
				.createQuery("FROM ProductsInCart where cartId = :cartId AND productId = :productId")
				.setParameter("cartId", userId).setParameter("productId", productId).list();
		System.out.println(productrQuantityCart.get(0).getCartId());
		productrQuantityCart.get(0).setProductQuantity(quantityOfProduct);
		getSession().save(productrQuantityCart.get(0));

		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Cacheable(value = "cart", key = "#userId")
	public UserCart userCart(int userId) throws DaoException {
		// TODO Auto-generated method stub
		if (userId == 0) {
			throw new DaoException("Authorization Required");
		}
		UserCart userCart = new UserCart();
		List<Integer> productIds = getSession()
				.createQuery("SELECT productId FROM ProductsInCart WHERE cartId = :cartId")
				.setParameter("cartId", userId).list();

		for (int i = 0; i < productIds.size(); i++) {

			ProductInCart productInCart = new ProductInCart();
			int prodId = productIds.get(i);

			List<Product> product = getSession().createQuery("FROM Product WHERE productId = :productId")
					.setParameter("productId", prodId).list();
			System.out.println(product.get(0));

			List<Integer> productQuantity = getSession().createQuery(
					"SELECT productQuantity FROM ProductsInCart WHERE productId = :productId AND cartId = :cartId")
					.setParameter("productId", prodId).setParameter("cartId", userId).list();
			System.out.println("Product quantity is " + productQuantity.get(0));
			productInCart.setProductId(product.get(0).getProductId());
			productInCart.setProdName(product.get(0).getProdName());
			productInCart.setPrice(product.get(0).getPrice());
			productInCart.setQuantity(productQuantity.get(0));

			userCart.getProductInCart().add(productInCart);

		}

		return userCart;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value = "cart", key = "#userId")
	public String removeProductFromCart(int userId, int prodId) throws DaoException {
		// TODO Auto-generated method stub
		if (userId == 0) {
			throw new DaoException("Authentication required");
		}
		List<ProductsInCart> productsInCart = getSession()
				.createQuery("FROM ProductsInCart WHERE cartId = ? AND productId = ?").setParameter(0, userId)
				.setParameter(1, prodId).list();
		if (productsInCart.get(0).getProductQuantity() == 1 || productsInCart.get(0).getProductQuantity() > 1) {
			getSession().delete(productsInCart.get(0));
			List<User> user = getSession().createQuery("FROM User WHERE userId = :userId")
					.setParameter("userId", userId).list();
			List<Product> products = getSession().createQuery(
					"SELECT products FROM Cart WHERE cartId = :cartId and products_productId = :products_productId")
					.setParameter("cartId", userId).setParameter("products_productId", prodId).list();
			user.get(0).getCart().getProducts().remove(products.get(0));
			getSession().update(user.get(0));
			getSession().save(user.get(0));
			return "Product is removed.";
		}
		return null; 

	}

	@Override
	@CacheEvict(value = "cache", allEntries= true)
	public void evictCache() {
		// TODO Auto-generated method stub
		
	} 
  
}    
