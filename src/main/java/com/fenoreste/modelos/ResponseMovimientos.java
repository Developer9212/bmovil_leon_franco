package com.fenoreste.modelos;

import java.io.Serializable;
import java.util.List;

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
	
	
	private static final long serialVersionUID = 1L;
	
	}
