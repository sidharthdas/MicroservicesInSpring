package com.sca.scaadminportal.dao;

import java.util.List;

import com.sca.scaadminportal.exception.DaoException;
import com.sca.scaadminportal.model.SuperAdmin;

public interface SuperAdminDao {
	
	public List<String> showAllAdmins() throws DaoException;
	public String loginSuperAdmin(SuperAdmin superAdmin) throws DaoException;
	public String signupSuperAdmin()throws DaoException;
	public String changePasswordOfAdmin(String adminName, String adminNewPassword) throws DaoException;
	public String deleteAdmin(String adminName) throws DaoException;

}
