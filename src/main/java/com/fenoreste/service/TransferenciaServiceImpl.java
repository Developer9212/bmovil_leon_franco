package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.TransferenciaDao;
import com.fenoreste.entity.Transferencia;

@Service
public class TransferenciaServiceImpl implements ITransferenciaService{
    
	@Autowired
	private TransferenciaDao transferenciaDao;
	
	@Override
	public Transferencia buscarPorId(String folio) {
		return transferenciaDao.findById(folio).orElse(null);
	}

	@Override
	public Transferencia guardar(Transferencia movimiento) {
		Transferencia tx = transferenciaDao.save(movimiento);
		return transferenciaDao.findById(tx.getFolioautorizacion()).orElse(null);
	}

}
