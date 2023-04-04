package com.fenoreste.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fenoreste.entity.AuxiliarD;
import com.fenoreste.entity.AuxiliarPK;


public interface IAuxiliarDService {
    
	public AuxiliarD buscarUltimoMovimiento(AuxiliarPK pk);
	
	public List<AuxiliarD>buscarTodosMovs(AuxiliarPK pk,Date fechaIni,Date fechaFin,Pageable pageable);
	
	public Integer contadorPorFecha(AuxiliarPK pk,Date fechaIni,Date fechaFin);
}
