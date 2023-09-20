package com.jot.JobOpportunity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Account")
public class Account extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
	private String username;

	@Column(name = "password", columnDefinition = "VARCHAR(100)", nullable = false)
	private String password;

	@Column(name = "authority", columnDefinition = "VARCHAR(20)", nullable = false)
	private String authority;

	@Column(name = "name", columnDefinition = "NVARCHAR(200)", nullable = false)
	private String name;

	@Column(name = "age", columnDefinition = "INT", nullable = false)
	private int age;

	@Column(name = "gender", columnDefinition = "BIT DEFAULT 0", nullable = false)
	private boolean gender;

	@Column(name = "tel", columnDefinition = "VARCHAR(15)", nullable = false)
	private String tel;

	@Column(name = "email", columnDefinition = "VARCHAR(50)", nullable = false)
	private String email;

	@Column(name = "is_del", columnDefinition = "BIT DEFAULT 0", nullable = false)
	private boolean isDel;

	public boolean isDel() {
		return isDel;
	}

	public void setDel(boolean del) {
		isDel = del;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
