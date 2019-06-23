package com.sca.ShoppingCartAppMind.model;

import javax.persistence.Entity;

@Entity
public class Book extends Product {

	private String genre;
	private String auther;
	private String publications;
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getAuther() {
		return auther;
	}
	public void setAuther(String auther) {
		this.auther = auther;
	}
	public String getPublications() {
		return publications;
	}
	public void setPublications(String publications) {
		this.publications = publications;
	}
}
