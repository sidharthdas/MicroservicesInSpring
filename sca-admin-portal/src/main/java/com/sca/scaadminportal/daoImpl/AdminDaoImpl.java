package com.sca.scaadminportal.daoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.sca.scaadminportal.dao.AdminDao;
import com.sca.scaadminportal.dto.AdminSession;
import com.sca.scaadminportal.dto.Apparal;
import com.sca.scaadminportal.dto.Book;
import com.sca.scaadminportal.exception.DaoException;
import com.sca.scaadminportal.model.Admin;

@Repository
@Transactional
public class AdminDaoImpl implements AdminDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private RestTemplate getRestTemplate;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public String adminLogin(Admin admin) throws DaoException {
		// TODO Auto-generated method stub
		List<Admin> admins = getSession().createQuery("FROM Admin where adminName = :adminName")
				.setParameter("adminName", admin.getAdminName()).list();
		if (admins.isEmpty()) {
			throw new DaoException("Not a admin.");
		} else if (admins.get(0).getPassword().equals(admin.getPassword())) {
			AdminSession.AdminName = admin.getAdminName();
			System.out.println(AdminSession.AdminName);
			return "logged in sucessfully";
		}
		return "Password didnt match";
	}

	@Override
	public String addAdmin(Admin admin) {
		// TODO Auto-generated method stub
		getSession().save(admin);
		
		return "Admin is added";
	}

	@Override
	public String addBook(Book book) throws DaoException {
		// TODO Auto-generated method stub
		return getRestTemplate.postForObject("http://localhost:9090//api/admin/add-book","post", String.class);
		//getRestTemplate.postFor("http://localhost:9090//api/admin/add-book", String.class)
		
	}

	@Override
	public String addApparal(Apparal apparal) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
