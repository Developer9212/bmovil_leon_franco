package com.fenoreste.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.FuncionDao;
import com.fenoreste.entity.AuxiliarPK;
import com.fenoreste.entity.MovimientoPasoPK;

@Service
public class FuncionServiceImpl implements IFuncionService {
     
	@Autowired
	private FuncionDao funcionDao;
	
	@Override
	public String montoParaLiquidarPrestamo(AuxiliarPK pk) {
		return funcionDao.monto_liquidacion_prestamo(pk.getIdorigenp(),pk.getIdproducto(),pk.getIdauxiliar());
	}

	@Override
	public String sai_auxiliar(AuxiliarPK pk) {
		return funcionDao.sai_auxiliar(pk.getIdorigenp(),pk.getIdproducto(),pk.getIdauxiliar());
	}

	@Override
	public String prestamo_cuanto(AuxiliarPK pk, Integer tipoAmortizacion) {
		return funcionDao.prestamo_cuanto(pk.getIdorigenp(),pk.getIdproducto(),pk.getIdauxiliar(),tipoAmortizacion);
	}

	@Override
	public String sesion() {
		return funcionDao.sesion();
	}

	@Override
	public String aplica_transaccion(MovimientoPasoPK pk) {
		return funcionDao.sai_procesa_transaccion(pk.getFecha(),pk.getIdusuario(),pk.getSesion(),pk.getReferencia());
	}

	@Override
	public void eliminarRegistrosProcesados(MovimientoPasoPK pk) {
		funcionDao.sai_bankingly_termina_transaccion(pk.getFecha(),pk.getIdusuario(),pk.getSesion(),pk.getReferencia());
	}


}
