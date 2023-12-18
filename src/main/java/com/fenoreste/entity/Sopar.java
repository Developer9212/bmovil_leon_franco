package com.fenoreste.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="sopar")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sopar implements Serializable {

	@EmbeddedId
	private PersonaPK pk;
	private String tipo;
	private String departamento;
	private String puesto;
	
    private static final long serialVersionUID = 1L;	
}
