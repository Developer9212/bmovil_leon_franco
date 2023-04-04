/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fenoreste.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @author Elliot
 */
@Entity
@Table(name = "tablas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tabla implements Serializable {    
	
	@EmbeddedId
    private TablaPK pk;
    private String nombre;
    private String dato1;
    private String dato2;
    private String dato3;
    private String dato4;
    private String dato5;
    private short tipo;

	private static final long serialVersionUID = 1L;

}
