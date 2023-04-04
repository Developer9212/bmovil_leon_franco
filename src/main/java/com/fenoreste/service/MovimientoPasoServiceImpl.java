package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.MovimientoPasoDao;
import com.fenoreste.entity.MovimientoPaso;

@Service
public class MovimientoPasoServiceImpl implements IMovimientoPasoService{
    
	@Autowired
	private MovimientoPasoDao movimientoPasoDao;
	
	@Override
	public MovimientoPaso guardar(MovimientoPaso movimiento) {
		return movimientoPasoDao.save(movimiento);
	}

	@Override
	public void eliminar(MovimientoPaso movimiento) {
		// TODO Auto-generated method stub
		
	}

}
