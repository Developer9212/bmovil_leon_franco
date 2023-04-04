package com.fenoreste.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.entity.AuxiliarD;
import com.fenoreste.entity.AuxiliarPK;

public interface AuxiliarDDao extends JpaRepository<AuxiliarD,AuxiliarPK> {
   
	@Query(value = "SELECT * FROM auxiliares_d WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3 ORDER BY fecha DESC LIMIT 1",nativeQuery = true)
	public AuxiliarD buscarUltimoMovimiento(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	@Query(value="SELECT * FROM auxiliares_d WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3 and date(fecha) between ?4 AND ?5 ORDER BY fecha DESC",nativeQuery = true)
	public List<AuxiliarD>todosMovimientos(Integer idorigenp,Integer idproducto,Integer idauxiliar,Date fechaIni,Date fechaFin,Pageable pageable);
	
	@Query(value="SELECT count(*) FROM auxiliares_d WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3 AND date(fecha) BETWEEN ?4 AND ?5",nativeQuery = true) 
	public Integer totalMovFecha(Integer idorigenp,Integer idproducto,Integer idauxiliar,Date fechaInicio,Date fechaFin);

	
}
