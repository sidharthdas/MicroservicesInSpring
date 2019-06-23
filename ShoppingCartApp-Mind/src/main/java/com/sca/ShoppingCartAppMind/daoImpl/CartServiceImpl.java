package com.sca.ShoppingCartAppMind.daoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.sca.ShoppingCartAppMind.dao.CartService;
import com.sca.ShoppingCartAppMind.dto.MyCart;
import com.sca.ShoppingCartAppMind.dto.ProductInCart;
import com.sca.ShoppingCartAppMind.exception.DaoException;
import com.sca.ShoppingCartAppMind.model.Apparal;
import com.sca.ShoppingCartAppMind.model.Book;
import com.sca.ShoppingCartAppMind.model.Product;
import com.sca.ShoppingCartAppMind.model.ProductQuantityCart;
import com.sca.ShoppingCartAppMind.model.User;


@Repository
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Boolean checkProductInCart(Long userId, int prodId) {
		List<User> user = getSession().createQuery("FROM User WHERE userId = :userId ").setParameter("userId", userId)
				.list();
		List<Product> product = getSession().createQuery("FROM Product where productId = :productId")
				.setParameter("productId", prodId).list();
		Boolean checkCart = user.get(0).getCart().getProducts().contains(product.get(0));
		System.out.println(checkCart);
		return checkCart;

	}

	public String addInproductQuantityCart(Long userId, int prodId) {
		List<ProductQuantityCart> productQuantityCart = getSession()
				.createQuery("FROM ProductQuantityCart WHERE cartId = :cartId AND productId = :productId")
				.setParameter("cartId", userId).setParameter("productId", prodId).list();
		int count = productQuantityCart.get(0).getProdQuantity() + 1;
		productQuantityCart.get(0).setProdQuantity(count);
		getSession().save(productQuantityCart.get(0));
		return "added sucesssfully";
	}

	@Override
	public String addProductToCart(Long userId, int prodId) throws DaoException {
		if (userId == null) {
			throw new DaoException("Authentication Required.");
		}

		if (checkProductInCart(userId, prodId)) {
			return addInproductQuantityCart(userId, prodId);
		}
		List<User> user = getSession().createQuery("FROM User where cart_cartId = :cartId")
				.setParameter("cartId", userId).list();
		List<Book> book = getSession().createQuery("FROM Book where productId = :productId")
				.setParameter("productId", prodId).list();
		if (book.isEmpty()) {
			List<Apparal> apparal = getSession().createQuery("FROM Apparal where productId = :productId")
					.setParameter("productId", prodId).list();
			user.get(0).getCart().getProducts().add(apparal.get(0));
			getSession().update(user.get(0));
			ProductQuantityCart pqc = new ProductQuantityCart();
			pqc.setCartId(userId);
			pqc.setProductId(prodId);
			pqc.setProdQuantity(1);
			getSession().save(pqc);
			return "Added sucessfully.";
		}
		user.get(0).getCart().getProducts().add(book.get(0));
		getSession().save(user.get(0));
		ProductQuantityCart pqc = new ProductQuantityCart();
		pqc.setCartId(userId);
		pqc.setProductId(prodId);
		pqc.setProdQuantity(1);
		getSession().save(pqc);
		return "Added sucessfully.";

	}

	@Override
	public String removeProductFromCart(Long userId, int prodId) throws DaoException {
		if (userId == null) {
			throw new DaoException("Authorization Required");
		}

		List<ProductQuantityCart> productQuantityCart = getSession()
				.createQuery("FROM ProductQuantityCart WHERE cartId =  :cartId").setParameter("cartId", userId).list();
		if (productQuantityCart.get(0).getProdQuantity() > 1) {
			int countQuantity = productQuantityCart.get(0).getProdQuantity() - 1;
			productQuantityCart.get(0).setProdQuantity(countQuantity);
			getSession().update(productQuantityCart.get(0));
			// getSession().save(object)
			return "1 quantity is removed";

		} else if (productQuantityCart.get(0).getProdQuantity() == 1) {
			getSession().delete(productQuantityCart.get(0));
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
	public String removeAllProductFromCart(Long userId) throws DaoException {
		if (userId == null) {
			throw new DaoException("Authorization Required");
		}
		List<ProductQuantityCart> productQuantityCart = getSession()
				.createQuery("FROM ProductQuantityCart WHERE cartId = :cartId").setParameter("cartId", userId).list();
		for (ProductQuantityCart p : productQuantityCart) {
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
	public MyCart viewMyCart(Long userId) throws DaoException {
		// TODO Auto-generated method stub
		if (userId == null) {
			throw new DaoException("Authorization Required");
		}
		MyCart myCart = new MyCart();
		double totalAmount = 0;
		List<Integer> productIds = getSession()
				.createQuery("SELECT productId FROM ProductQuantityCart WHERE cartId = :cartId")
				.setParameter("cartId", userId).list(); 

		for (int i = 0; i < productIds.size(); i++) {

			ProductInCart productInCart = new ProductInCart();
			int prodId = productIds.get(i);

			List<Product> product = getSession().createQuery("FROM Product WHERE productId = :productId")
					.setParameter("productId", prodId).list();
			System.out.println(product.get(0));

			List<Integer> productQuantity = getSession().createQuery(
					"SELECT prodQuantity FROM ProductQuantityCart WHERE productId = :productId AND cartId = :cartId")
					.setParameter("productId", prodId).setParameter("cartId", userId).list();
			System.out.println("Product quantity is " + productQuantity.get(0));
			productInCart.setProductId(product.get(0).getProductId());
			productInCart.setProdName(product.get(0).getProdName());
			productInCart.setPrice(product.get(0).getPrice());
			productInCart.setQuantity(productQuantity.get(0));

			myCart.getProductInCart().add(productInCart);

			totalAmount = totalAmount + (product.get(0).getPrice() * productQuantity.get(0));

		}
		System.out.println(totalAmount);
		myCart.setTotalAmount(totalAmount);
		return myCart;
	}

	@Override
	public String changeQuantityOfProductByNumber(int quantityOfProduct, int productId, Long userId)
			throws DaoException {
		// TODO Auto-generated method stub 
		if (userId == null) {   
			throw new DaoException("Authentication required");  
		}
		if (quantityOfProduct == 0) {
			List<User> user = getSession().createQuery("FROM User WHERE userId = :userId")
					.setParameter("userId", userId).list();
			List<Product> product = getSession().createQuery("FROM Product WHERE productId = :productId")
					.setParameter("productId", productId).list();
			user.get(0).getCart().getProducts().remove(product.get(0)); 
			getSession()
					.createQuery("DELETE FROM ProductQuantityCart where cartId = :cartId AND productId = :productId")
					.setParameter("cartId", userId).setParameter("productId", productId).executeUpdate();
			return "Product removed sucessfully";
		}
		System.out.println(userId);
		System.out.println(productId);
		System.out.println(quantityOfProduct);
		List<ProductQuantityCart> productrQuantityCart = getSession()
				.createQuery("FROM ProductQuantityCart where cartId = :cartId AND productId = :productId")
				.setParameter("cartId", userId).setParameter("productId", productId).list();
		System.out.println(productrQuantityCart.get(0).getCartId());
		productrQuantityCart.get(0).setProdQuantity(quantityOfProduct);
		getSession().save(productrQuantityCart.get(0));

		return null;
	}

}
