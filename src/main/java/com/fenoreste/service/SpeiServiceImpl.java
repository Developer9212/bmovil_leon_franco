package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.SpeiAbonoDao;
import com.fenoreste.dao.SpeiDispersionDao;
import com.fenoreste.entity.SpeiAbono;
import com.fenoreste.entity.SpeiDispersion;

@Service
public class SpeiServiceImpl implements ISpeiService{
    
	@Autowired
	private SpeiDispersionDao speiDao;
	@Autowired
	private SpeiAbonoDao abonoDao;
	
	@Override
	public SpeiDispersion buscarPorId(String folio) {
		return speiDao.findById(folio).orElse(null);
	}

	@Override
	public SpeiDispersion guardar(SpeiDispersion mov) {
		speiDao.save(mov);
		return speiDao.findById(mov.getFoliosolicitante()).orElse(null);
	}

	@Override
	public SpeiAbono buscarPorIdAbono(String claveRastreo) {
		return abonoDao.findById(claveRastreo).orElse(null);
	}

	@Override
	public SpeiAbono guardar(SpeiAbono mov) {
		return abonoDao.save(mov);
	}

}
