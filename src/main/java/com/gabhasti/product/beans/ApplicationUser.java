package com.gabhasti.product.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "USER_TABLE")
public class ApplicationUser implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id	
	private String username;

	private String password;
	
	private String email;
	
	private String status;
	
	private String resetFrequency;
	
	private Date userExpiryDate;
	
	private long opportunityId;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "username",name = "username")
	private List<ApplicationRoles> roles;

	
	
	public List<ApplicationRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<ApplicationRoles> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResetFrequency() {
		return resetFrequency;
	}

	public void setResetFrequency(String resetFrequency) {
		this.resetFrequency = resetFrequency;
	}

	public Date getUserExpiryDate() {
		return userExpiryDate;
	}

	public void setUserExpiryDate(Date userExpiryDate) {
		this.userExpiryDate = userExpiryDate;
	}

	public long getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(long opportunityId) {
		this.opportunityId = opportunityId;
	}	
	
	
}
