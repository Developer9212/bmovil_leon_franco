package com.fenoreste.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fenoreste.dao.AbonoSpeiDao;
import com.fenoreste.dao.MovimientoPasoDao;
import com.fenoreste.entity.MovimientoPaso;
import com.fenoreste.entity.SpeiAbonoPaso;

@Service
public class MovimientoPasoServiceImpl implements IMovimientoPasoService{
    
	@Autowired
	private MovimientoPasoDao movimientoPasoDao;
	@Autowired
	private AbonoSpeiDao abonoSpeiDao;
	
	@Override
	public MovimientoPaso guardar(MovimientoPaso movimiento) {
		return movimientoPasoDao.save(movimiento);
	}

	@Override
	public void eliminar(MovimientoPaso movimiento) {
		 movimientoPasoDao.delete(movimiento);
	}

	@Override
	public SpeiAbonoPaso guardar(SpeiAbonoPaso abono) {
		return abonoSpeiDao.save(abono);
	}

	@Override
	@Transactional
	public void eliminar(SpeiAbonoPaso abono) {
		abonoSpeiDao.eliminar(abono.getPk().getIdusuario(), abono.getPk().getReferencia(),abono.getPk().getSesion());		
	}

}
