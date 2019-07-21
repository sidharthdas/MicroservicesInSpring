package com.sca.scaadminportal.daoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sca.scaadminportal.dao.SuperAdminDao;
import com.sca.scaadminportal.dto.AdminSession;
import com.sca.scaadminportal.exception.DaoException;
import com.sca.scaadminportal.model.Admin;
import com.sca.scaadminportal.model.SuperAdmin;

@Repository
@Transactional
public class SuperAdminDaoImpl implements SuperAdminDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<String> showAllAdmins() throws DaoException {
		// TODO Auto-generated method stub
		if (AdminSession.SuperAdminName == null) {
			throw new DaoException("Need Super Admin Access.");
		}
		List<String> admins = getSession().createQuery("SELECT adminName FROM Admin").list();
		return admins;
	}

	@Override
	public String loginSuperAdmin(SuperAdmin superAdmin) throws DaoException {
		// TODO Auto-generated method stub
		List<SuperAdmin> superAdmins = getSession()
				.createQuery("FROM SuperAdmin WHERE superAdminName = :superAdminName")
				.setParameter("superAdminName", superAdmin.getSuperAdminName()).list();
		if (superAdmins == null) {
			throw new DaoException("Not a super admin.");
		}
		List<String> passwords = getSession()
				.createQuery("SELECT superAdminPassword FROM SuperAdmin WHERE superAdminName = :superAdminName")
				.setParameter("superAdminName", superAdmin.getSuperAdminName()).list();
		if (passwords.get(0).equals(superAdmin.getSuperAdminPassword())) {
			AdminSession.SuperAdminName = superAdmin.getSuperAdminName();
			System.out.println("The present superAdmin is " + AdminSession.SuperAdminName);
			return "Sucess";
		} else {
			throw new DaoException("password didnt match.");
		}
	}

	@Override
	public String signupSuperAdmin() throws DaoException {
		// TODO Auto-generated method stub
		SuperAdmin superAdmin = new SuperAdmin();
		superAdmin.setSuperAdminName("superadmin");
		superAdmin.setSuperAdminPassword("superadmin123");
		getSession().save(superAdmin);
		return "Added";
	}

	@Override
	public String changePasswordOfAdmin(String adminName, String adminNewPassword) throws DaoException {
		// TODO Auto-generated method stub
		if (AdminSession.SuperAdminName == null) {
			throw new DaoException("Need SuperAdmin Access.");
		}
		List<Admin> admins = getSession().createQuery("FROM Admin WHERE adminName = :adminName")
				.setParameter("adminName", adminName).list();
		if (admins == null) {
			throw new DaoException("Admin not present");
		} else {
			admins.get(0).setPassword(adminNewPassword);
			getSession().update(admins.get(0));
			return "Password is updated";

		}

	}

	@Override
	public String deleteAdmin(String adminName) throws DaoException {
		// TODO Auto-generated method stub
		if (AdminSession.SuperAdminName == null) {
			throw new DaoException("Super admin access required.");
		}
		List<Admin> admins = getSession().createQuery("FROM Admin WHERE adminName = :adminName")
				.setParameter("adminName", adminName).list();
		System.out.println(admins.isEmpty());

		if (admins.isEmpty()) {
			return "Admin not persent with the name " + adminName;
		}else {
			getSession().delete(admins.get(0));
			return "Admin is deleted";
		}
		
	}

}
