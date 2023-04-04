package com.fenoreste.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoPasoPK implements Serializable{
     
	 @Column(name="fecha")
	 private Timestamp fecha;
	 @Column(name = "idusuario")
	 private Integer idusuario;
	 @Column(name = "sesion")
	 private String sesion;
	 @Column(name = "referencia")
	 private String referencia;
	 @Column(name="idorigenp")
	 private Integer idorigenp;    
	 @Column(name="idproducto")
	 private Integer idproducto;
	 @Column(name = "idauxiliar")
	 private Integer idauxiliar;
	
	 
	 
	 
	 
	 private static final long serialVersionUID = 1L;
}
