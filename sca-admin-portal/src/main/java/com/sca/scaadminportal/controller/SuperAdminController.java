package com.sca.scaadminportal.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sca.scaadminportal.dao.SuperAdminDao;
import com.sca.scaadminportal.exception.DaoException;
import com.sca.scaadminportal.model.SuperAdmin;

@RestController
@RequestMapping("/api/super-admin/")
public class SuperAdminController {

	@Autowired
	private SuperAdminDao superAdminDao;

	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test() {
		return "SuperAdmin Controller is working. . .";
	}

	@RequestMapping(value = "add-super-admin", method = RequestMethod.POST)
	public ResponseEntity<String> addSuperAdmin(HttpServletRequest request) {
		try {
			return new ResponseEntity<String>(superAdminDao.signupSuperAdmin(), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "login-super-admin", method = RequestMethod.POST)
	public ResponseEntity<String> loginSuperAdmin(@RequestBody SuperAdmin superAdmin, HttpServletRequest request) {
		try {
Map<String, String> map = new HashMap<String, String>();
			

			Enumeration headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String key = (String) headerNames.nextElement();
				String value = request.getHeader(key);
				map.put(key, value);
				
			}
			if(map.isEmpty()) {
				System.out.println("empty");
			}
			
			System.out.println(map.get("authorization"));
			return new ResponseEntity<String>(superAdminDao.loginSuperAdmin(superAdmin), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "all-admins", method = RequestMethod.GET)
	public ResponseEntity<List<String>> allAdmins() {
		try {
			return new ResponseEntity<List<String>>(superAdminDao.showAllAdmins(), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<List<String>>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "change-admin-password", method = RequestMethod.PUT)
	public ResponseEntity<String> changePasswordADMIN(@RequestParam(value = "name", required = true) String name,
			@RequestBody String adminNewPassword) {
		try {
			return new ResponseEntity<String>(superAdminDao.changePasswordOfAdmin(name, adminNewPassword),
					HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "delete-admin", method = RequestMethod.PUT)
	public ResponseEntity<String> deleteAdmin(@RequestParam(value = "name", required = true) String adminName) {
		try {
			return new ResponseEntity<String>(superAdminDao.deleteAdmin(adminName), HttpStatus.OK);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

}
