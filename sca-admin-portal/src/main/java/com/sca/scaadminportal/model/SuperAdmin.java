package com.sca.scaadminportal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SuperAdmin {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String superAdminName;
	private String superAdminPassword;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSuperAdminName() {
		return superAdminName;
	}

	public void setSuperAdminName(String superAdminName) {
		this.superAdminName = superAdminName;
	}

	public String getSuperAdminPassword() {
		return superAdminPassword;
	}

	public void setSuperAdminPassword(String superAdminPassword) {
		this.superAdminPassword = superAdminPassword;
	}

}
