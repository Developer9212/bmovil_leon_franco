package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.PersonaDao;
import com.fenoreste.entity.Persona;
import com.fenoreste.entity.PersonaPK;

@Service
public class PersonaServiceImpl implements IPersonaService{
	
	@Autowired
	private PersonaDao personaDao;
	
	@Override
	public Persona buscarPorId(PersonaPK pk) {
		return personaDao.findById(pk).orElse(null);
	}

}
