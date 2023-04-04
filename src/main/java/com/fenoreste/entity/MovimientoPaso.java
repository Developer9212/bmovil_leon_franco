package com.fenoreste.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author wilmer
 */
@Entity
@Table(name = "bankingly_movimientos_ca")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoPaso implements Serializable {
    @EmbeddedId
    private MovimientoPasoPK pk;
    @Column(name = "idorigen")
    private Integer idorigen;
    @Column(name = "idgrupo")
    private Integer idgrupo;
    @Column(name = "idsocio")
    private Integer idsocio;
    @Column(name = "cargoabono")
    private Integer cargoabono;
    @Column(name = "monto")
    private Double monto;
    @Column(name = "idcuenta")
    private String idcuenta;
    @Column(name = "iva")
    private Double iva;
    @Column(name = "tipo_amort")
    private Integer tipo_amort;   
    @Column(name = "sai_aux")
    private String sai_aux;

	private static final long serialVersionUID = 1L;

    
}
