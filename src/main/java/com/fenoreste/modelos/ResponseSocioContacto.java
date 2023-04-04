package com.fenoreste.modelos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSocioContacto {

	private String correoElectronico;
	private String movil;
}
