package org.clon.apps;

public class ClientInfo {
	private String id;
	private String pw;
	private String name;

	public void setid(String id) {
		this.id = id;
	}

	public void setpw(String pw) {
		this.pw = pw;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getid() {
		return this.id;
	}

	public String getname() {
		return this.name;
	}

	public String getpw() {
		return this.pw;
	}
}
