package com.fenoreste.modelos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movimiento implements Serializable {
	
	private Integer id;
	private String tipo;
	private String fechaTransaccion;
	private String fechaPublicacion;
	private Double monto;
	private String descripcion;
	
	
	private static final long serialVersionUID = 1L;

}
