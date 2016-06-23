package com.hank.pojo;

public class User {
	private int id;
	private String username;
	private String password;

	public final static int TYPE_ADMIN = 0;
	public final static int TYPE_ENTERPRISE = 1;
	public final static int TYPE_PERSON = 2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", username=" + username + ", password="
				+ password + "]";
	}
}
