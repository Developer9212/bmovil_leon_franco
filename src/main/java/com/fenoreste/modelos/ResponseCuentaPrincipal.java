package com.fenoreste.modelos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class ResponseCuentaPrincipal implements Serializable{
   
	private List<ResponseCuenta> cuentas;
	private ResponsePaginacion paginacion;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private int codigo;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String mensaje;
	private static final long serialVersionUID = 1L;
}
