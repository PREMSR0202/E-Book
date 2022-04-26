package com.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String fname;
	private String lname;
	private String email;
	private String pwd;
	private boolean account;
	private String invoice;
	
	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "role_for_users")
	private Role role;
	
	@Column(name = "reset_password_token")
	private String resetPasswordToken;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isAccount() {
		return account;
	}

	public void setAccount(boolean account) {
		this.account = account;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	@Override
	public String toString() {
		return "User [account=" + account + ", email=" + email + ", fname=" + fname + ", id=" + id + ", invoice="
				+ invoice + ", lname=" + lname + ", pwd=" + pwd + ", resetPasswordToken=" + resetPasswordToken
				+ ", role=" + role + "]";
	}

	public User(int id, String fname, String lname, String email, String pwd, boolean account, String invoice,
			Role role, String resetPasswordToken) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.pwd = pwd;
		this.account = account;
		this.invoice = invoice;
		this.role = role;
		this.resetPasswordToken = resetPasswordToken;
	}

	public User() {
		
	}
}