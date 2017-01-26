package com.pl.dto;

public class AuthorUrl {

	public AuthorUrl() {}
	
	public AuthorUrl(String author, String url) {
		super();
		this.author = author;
		this.url = url;
	}
	
	private String author;
	private String url;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
