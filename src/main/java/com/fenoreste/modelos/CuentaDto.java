package com.fenoreste.modelos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class CuentaDto {
	@JsonInclude(value = Include.NON_EMPTY)
	private String numeroSocio;
	private String numeroCuenta;
	private String subtipoCuenta;
	
}
