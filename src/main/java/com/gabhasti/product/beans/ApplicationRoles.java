package com.gabhasti.product.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER_ROLES",uniqueConstraints = @UniqueConstraint(columnNames = {"authority","username"}))
public class ApplicationRoles implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_USER_ROLES",sequenceName = "SEQ_USER_ROLES",initialValue = 1,allocationSize = 1 )
	@GeneratedValue(generator = "SEQ_USER_ROLES",strategy = GenerationType.SEQUENCE)
	private int id ;
	
	private String authority;
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@Override
	public String getAuthority() {
		return authority;
	}
	@JsonIgnore
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
		
	
	
}
