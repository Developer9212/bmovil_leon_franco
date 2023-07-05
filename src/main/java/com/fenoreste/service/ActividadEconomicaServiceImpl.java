package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.ActividadEconomicaDao;
import com.fenoreste.entity.ActividadEconomica;

@Service
public class ActividadEconomicaServiceImpl implements IActividadEconomicaService{
    
	@Autowired
	private ActividadEconomicaDao actividadEconomicaDao;
	
	@Override
	public ActividadEconomica buscarPorId(Integer id) {
		return actividadEconomicaDao.findById(id).orElse(null);
	}

}
