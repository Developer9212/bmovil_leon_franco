package com.fenoreste.service;

import com.fenoreste.entity.PersonaPK;
import com.fenoreste.entity.Trabajo;

public interface ITrabajoService {
   
	public Trabajo buscarPorId(PersonaPK pk);
}
