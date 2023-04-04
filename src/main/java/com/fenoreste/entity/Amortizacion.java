/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "amortizaciones")
@Cacheable(false)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Amortizacion implements Serializable {   

	private static final long serialVersionUID = 1L;
    
	@EmbeddedId AuxiliarPK pk;
	@Column(name="idamortizacion")
    private Integer id;        
    @Column(name = "vence")
    @Temporal(TemporalType.DATE)
    private Date vence;
    @Column(name = "abono")
    private BigDecimal abono;
    @Column(name = "io")
    private BigDecimal io;
    @Column(name = "abonopag")
    private BigDecimal abonopag;
    @Column(name = "iopag")
    private BigDecimal iopag;
    @Column(name = "bonificado")
    private Boolean bonificado;
    @Column(name = "pagovariable")
    private Boolean pagovariable;
    @Column(name = "todopag")
    private Boolean todopag;
    @Column(name = "atiempo")
    private Boolean atiempo;
    @Column(name = "bonificacion")
    private BigDecimal bonificacion;
    @Column(name = "anualidad")
    private BigDecimal anualidad;
    @Column(name = "diasvencidos")
    private Integer diasvencidos;

    
}
