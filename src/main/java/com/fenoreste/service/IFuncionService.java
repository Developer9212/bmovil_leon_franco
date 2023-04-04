package com.fenoreste.service;

import com.fenoreste.entity.AuxiliarPK;
import com.fenoreste.entity.MovimientoPasoPK;

public interface IFuncionService {
   
	public String montoParaLiquidarPrestamo(AuxiliarPK pk);
	public String sai_auxiliar(AuxiliarPK pk);
	public String prestamo_cuanto(AuxiliarPK pk,Integer tipoAmortizacion);
	public String sesion();
	public String aplica_transaccion(MovimientoPasoPK pk);
	public void eliminarRegistrosProcesados(MovimientoPasoPK pk);
}
