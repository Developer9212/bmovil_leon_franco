package com.fenoreste.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.entity.SpeiAbonoPaso;
import com.fenoreste.entity.AbonoSpeiPK;

public interface AbonoSpeiDao extends JpaRepository<SpeiAbonoPaso,AbonoSpeiPK>{
   
	@Modifying
	@Query(value = "DELETE FROM spei_entrada_temporal_cola_guardado WHERE idusuario=?1 AND referencia=?2 and sesion=?3",nativeQuery = true)
	void eliminar(Integer idusuario,String referencia,String sesion);
}
