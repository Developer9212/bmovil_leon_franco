package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.TrabajoDao;
import com.fenoreste.entity.PersonaPK;
import com.fenoreste.entity.Trabajo;

@Service
public class TrabajoServiceImpl implements ITrabajoService{
    
	@Autowired
	private TrabajoDao trabajoDao;
	
	@Override
	public Trabajo buscarPorId(PersonaPK pk) {
		return trabajoDao.buscarPorOgs(pk.getIdorigen(), pk.getIdgrupo(),pk.getIdsocio());
	}

}
