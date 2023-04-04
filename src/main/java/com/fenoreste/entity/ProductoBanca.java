package com.fenoreste.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="productos_banca_movil")
@Data
public class ProductoBanca implements Serializable{
     
	@Id
	private Integer idproducto;
	private Integer tipoproducto;
	private String nombreproducto;
	private boolean consulta;
	private boolean retiro;
	private boolean deposito;
	private static final long serialVersionUID = 1L;
}
