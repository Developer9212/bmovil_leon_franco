package com.fenoreste.modelos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class RegistroTransaccion {
  private String fechaRegistro;	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String fechaSolicitud;
}
