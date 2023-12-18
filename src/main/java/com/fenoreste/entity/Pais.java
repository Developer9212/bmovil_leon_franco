/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.entity;

import java.io.Serializable;
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
@Table(name = "paises")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pais implements Serializable {

    @Id
    private Integer idpais;
    private String nombre;
    
    private static final long serialVersionUID = 1L;   
}
