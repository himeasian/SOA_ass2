package au.edu.unsw.soacourse.foundit.bean;

public class Register {
	
	private String fname;
	private String lname;
	private String email;
	private String password;
	private String role;
	private String company;
	
	public String getFname() {
		return fname;
	}
	public String getLname() {
		return lname;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getRole() {
		return role;
	}
	public String getCompany() {
		return company;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setCompany(String company) {
		this.company = company;
	}
}
