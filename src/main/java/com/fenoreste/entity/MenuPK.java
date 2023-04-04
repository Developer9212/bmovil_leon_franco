package com.fenoreste.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuPK implements Serializable{
	
	private String menu;
	private Integer opcion;
	private static final long serialVersionUID = 1L;
}
