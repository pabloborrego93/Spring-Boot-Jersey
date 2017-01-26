package com.pl.dto;

import java.util.ArrayList;
import java.util.List;

public class Data {

	public Data() {}
	
	public Data(List<AuthorUrl> data) {
		this.data = data;
	}

	private List<AuthorUrl> data = new ArrayList<AuthorUrl>();

	public List<AuthorUrl> getData() {
		return data;
	}

	public void setData(List<AuthorUrl> data) {
		this.data = data;
	}
	
}
