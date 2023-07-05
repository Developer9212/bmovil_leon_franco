package com.fenoreste.modelos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMovimientos implements Serializable {    
	private String numeroCuenta;
	private String tipoCuenta;
	private String subtipoCuenta;
	private List<Movimiento>movimientos;
	private ResponsePaginacion paginacion;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private int codigo;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String mensaje;
	
	private static final long serialVersionUID = 1L;
	
	}
