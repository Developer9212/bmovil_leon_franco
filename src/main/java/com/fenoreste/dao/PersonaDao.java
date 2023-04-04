package com.fenoreste.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.entity.Persona;
import com.fenoreste.entity.PersonaPK;

public interface PersonaDao  extends JpaRepository<Persona,PersonaPK>{

}
