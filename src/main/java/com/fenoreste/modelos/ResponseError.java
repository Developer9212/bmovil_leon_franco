package com.fenoreste.modelos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError implements Serializable{
    
	private String codigo;
	private String mensajeUsuario;
    
	private static final long serialVersionUID = 1L;
}
