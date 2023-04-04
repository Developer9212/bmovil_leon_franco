package com.fenoreste.modelos;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ResponseCuentaPrincipal implements Serializable{
   
	private List<ResponseCuenta> cuentas;
	private ResponsePaginacion paginacion;
	
	private static final long serialVersionUID = 1L;
}
