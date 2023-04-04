package com.fenoreste.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.entity.Amortizacion;
import com.fenoreste.entity.AuxiliarPK;

public interface AmortizacionDao extends JpaRepository<Amortizacion,AuxiliarPK> {

	@Query(value = "SELECT * FROM amortizaciones a WHERE a.idorigenp=?1 AND a.idproducto=?2 AND a.idauxiliar=?3 ORDER BY vence ASC LIMIT 1",nativeQuery = true)
	Amortizacion buscarPrimeraAmortizacion(Integer idorigenp,Integer idproducto,Integer idauxiliar); 
	
	List<Amortizacion>findBypkAndTodopag(AuxiliarPK pk,Boolean estatus);
	
	@Query(value = "SELECT * FROM amortizaciones a WHERE a.idorigenp=?1 AND a.idproducto=?2 AND a.idauxiliar=?3 ORDER BY vence DESC LIMIT 1",nativeQuery = true)
	Amortizacion buscarUltimaAmortizacion(Integer idorigenp,Integer idproducto,Integer idauxiliar);
}
