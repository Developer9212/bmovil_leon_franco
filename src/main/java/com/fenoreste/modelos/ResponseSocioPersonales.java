package com.fenoreste.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSocioPersonales {
	
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String curp;
	private String genero;
	private String fechaNacimiento;
	private String domicilio;
}
