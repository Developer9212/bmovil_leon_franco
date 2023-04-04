package com.fenoreste.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TablaPK implements Serializable{

	private String idtabla;
	private String idelemento;
	
	private static final long serialVersionUID = 1L;
}
