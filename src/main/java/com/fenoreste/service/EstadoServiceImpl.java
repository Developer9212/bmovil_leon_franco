package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.EstadoDao;
import com.fenoreste.entity.Estado;

@Service
public class EstadoServiceImpl implements IEstadoService{

	@Autowired
	private EstadoDao estadoDao;
	
	@Override
	public Estado buscarPorId(Integer id) {
		return estadoDao.findById(id).orElse(null);
	}

	
}
