package com.fenoreste.service;

import com.fenoreste.entity.Persona;
import com.fenoreste.entity.PersonaPK;

public interface IPersonaService {

	public Persona buscarPorId(PersonaPK pk);
}
