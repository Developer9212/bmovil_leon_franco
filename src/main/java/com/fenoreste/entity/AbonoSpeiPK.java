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
public class AbonoSpeiPK implements Serializable {  
	private Integer idusuario;
	private String sesion;
	private String referencia;
	private Integer mov = 0;
	
	
	private static final long serialVersionUID = 1L;
}
