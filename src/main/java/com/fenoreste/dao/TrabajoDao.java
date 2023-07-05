package com.fenoreste.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.entity.PersonaPK;
import com.fenoreste.entity.Trabajo;

public interface TrabajoDao extends JpaRepository<Trabajo, PersonaPK>{

}
