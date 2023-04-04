package com.fenoreste.modelos;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponseSaldo implements Serializable{
    
	private String tipo;
	private Double monto;
	private String moneda;
	
	private static final long serialVersionUID = 1L;
}
