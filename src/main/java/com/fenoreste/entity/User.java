package com.fenoreste.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable{
  
	@Id
	private Integer id;	
	private String username;
	private String password;
	@Temporal(TemporalType.DATE)
	private Date create_at;
	
	@PrePersist 
	public void prePersist() {
		create_at = new Date();		
		Random random = new Random();
		int value = random.nextInt(50 + 1) +1;
		id = value;
		
	}	

	
	private static final long serialVersionUID = 1L;
	
}
