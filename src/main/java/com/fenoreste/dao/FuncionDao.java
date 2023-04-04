package com.fenoreste.dao;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.entity.Auxiliar;

public interface FuncionDao extends JpaRepository<Auxiliar,Integer>{

	@Query(value = "SELECT monto_para_liquidar_prestamo(?1,?2,?3,(SELECT date(fechatrabajo) FROM origenes LIMIT 1))",nativeQuery = true)
	String monto_liquidacion_prestamo(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	@Query(value = "SELECT sai_auxiliar(?1,?2,?3,(SELECT date(fechatrabajo) FROM origenes LIMIT 1))",nativeQuery = true)
	String sai_auxiliar(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	@Query(value = "SELECT sai_bankingly_prestamo_cuanto(?1,?2,?3,(SELECT date(fechatrabajo) FROM origenes LIMIT 1),?4,(SELECT sai_auxiliar(?1,?2,?3,(SELECT date(fechatrabajo) FROM origenes LIMIT 1))))",nativeQuery = true)
	String prestamo_cuanto(Integer idorigenp,Integer idproducto,Integer idauxiliar,Integer tipo);
	
	@Query(value ="SELECT sai_bankingly_aplica_transaccion(?1,?2,?3,?4)",nativeQuery = true)
	String sai_procesa_transaccion(Date fecha,Integer idusuario,String sesion,String referencia);

	@Query(value="SELECT sai_bankingly_termina_transaccion(?1,?2,?3,?4)",nativeQuery = true)
	String sai_bankingly_termina_transaccion(Date fecha,Integer idusuario,String sesion,String referencia);
	
	@Query(value = "SELECT text(pg_backend_pid())||'-'||trim(to_char(now(),'ddmmyy'))" , nativeQuery = true)
	String sesion();
	
	@Query(value ="SELECT sai_bankingly_servicio_activo_inactivo()" , nativeQuery = true)
	boolean servicios_activos();
}

