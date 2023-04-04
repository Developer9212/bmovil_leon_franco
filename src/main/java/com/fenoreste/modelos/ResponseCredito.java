package com.fenoreste.modelos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCredito implements Serializable {
	
	private Double tasaAnual;
	private Integer plazosRestantes;
	private Integer plazosTotales;
	private String unidadPlazo;
	private String fechaOrigen;
	private String fechaTermino;
	private ResponsePago proximoPago;
	private ResponsePago ultimoPago;

	private static final long serialVersionUID = 1L;
}
