package com.fenoreste.modelos;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponsePago implements Serializable{
    
	private String fecha;
	private Double monto;
	private String moneda;
	
	
	private static final long serialVersionUID = 1L;
}
