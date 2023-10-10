package com.fenoreste.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.entity.PersonaPK;
import com.fenoreste.entity.Trabajo;

public interface TrabajoDao extends JpaRepository<Trabajo, PersonaPK>{

	@Query(value = "SELECT * FROM trabajo WHERE idorigen=?1 AND idgrupo = ?2 AND idsocio = ?3 ORDER BY fechaingreso DESC LIMIT 1",nativeQuery = true)
	public Trabajo buscarPorOgs(Integer idorigen,Integer idgrupo,Integer idsocio);
}
