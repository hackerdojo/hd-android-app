package com.hackerdojo.android.person;

import java.util.Date;

public class Person implements Comparable<Person> {
	private String name;
	private Date created;
	private String imageUrl;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	@Override
	public int compareTo(Person arg0) {
		return this.getName().compareTo(arg0.getName());
	}
}
