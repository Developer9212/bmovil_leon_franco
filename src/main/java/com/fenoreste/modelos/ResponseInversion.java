package com.fenoreste.modelos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseInversion implements Serializable {
    
	private String fechaVencimiento;
	
	private final static long serialVersionUID = 1L;
}
