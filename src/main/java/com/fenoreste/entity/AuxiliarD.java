/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Elliot
 */

@Entity
@Table(name = "auxiliares_d")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuxiliarD implements Serializable{

    @Id
    private Integer transaccion; 
    private AuxiliarPK pk;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private Short cargoabono;
    private BigDecimal monto;
    private BigDecimal montoio;
    private BigDecimal montoim;
    private BigDecimal montoiva;
    private Integer idorigenc;
    private String periodo;
    private Short idtipo;
    private Integer idpoliza;
    private Short tipomov;
    private BigDecimal saldoec;
    private BigDecimal montoivaim;
    private BigDecimal efectivo;
    private int diasvencidos;
    private BigDecimal montovencido;
    private Integer ticket;
    private BigDecimal montoidnc;
    private BigDecimal montoieco;
    private BigDecimal montoidncm;
    private BigDecimal montoiecom;
  
    private static final long serialVersionUID = 1L;
    

}

