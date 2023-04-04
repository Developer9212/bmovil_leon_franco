package com.fenoreste.service;

import java.util.List;

import com.fenoreste.entity.Amortizacion;
import com.fenoreste.entity.AuxiliarPK;

public interface IAmortizacionService {

	public Amortizacion buscarPrimerPago(AuxiliarPK pk);
	public List<Amortizacion>pagadas(AuxiliarPK pk);
	public Amortizacion buscarUltimoPago(AuxiliarPK pk);
}
