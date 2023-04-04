package com.fenoreste.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.entity.Origen;

public interface OrigenDao extends JpaRepository<Origen,Integer>{

	@Query(value="SELECT * FROM origenes WHERE matriz=0",nativeQuery = true)
	public Origen buscarMatriz();
}
