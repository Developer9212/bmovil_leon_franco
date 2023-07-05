package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.ColoniaDao;
import com.fenoreste.entity.Colonia;

@Service
public class ColoniaServiceImpl implements IColoniaService{

	@Autowired
	public ColoniaDao coloniaDao;
	
	@Override
	public Colonia buscarPorId(Integer id) {
		return coloniaDao.findById(id).orElse(null);
	}

}
