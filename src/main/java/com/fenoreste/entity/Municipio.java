/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author wilmer
 */

@Entity
@Table(name = "municipios")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Municipio implements Serializable {

    @Id
    private Integer idmunicipio;
    private String nombre;
    private Integer idestado;
    private Integer poblacion;
    private BigInteger localidad_siti;
    private String de_cp;
    private String a_cp;
    
    private static final long serialVersionUID = 1L;    
    
}
