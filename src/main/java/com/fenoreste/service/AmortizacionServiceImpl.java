package com.fenoreste.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.AmortizacionDao;
import com.fenoreste.entity.Amortizacion;
import com.fenoreste.entity.AuxiliarPK;

@Service
public class AmortizacionServiceImpl implements IAmortizacionService{
    
	@Autowired
	private AmortizacionDao amortizacionDao;
	
	@Override
	public Amortizacion buscarPrimerPago(AuxiliarPK pk) {
		return amortizacionDao.buscarPrimeraAmortizacion(pk.getIdorigenp(),pk.getIdproducto(),pk.getIdauxiliar());
	}

	@Override
	public List<Amortizacion> pagadas(AuxiliarPK pk) {
		return amortizacionDao.findBypkAndTodopag(pk,true);
	}

	@Override
	public Amortizacion buscarUltimoPago(AuxiliarPK pk) {
		return amortizacionDao.buscarUltimaAmortizacion(pk.getIdorigenp(),pk.getIdproducto(),pk.getIdauxiliar());
	}

}
