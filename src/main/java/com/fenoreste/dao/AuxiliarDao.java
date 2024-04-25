package com.fenoreste.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.entity.Auxiliar;
import com.fenoreste.entity.AuxiliarPK;

public interface AuxiliarDao extends JpaRepository<Auxiliar, AuxiliarPK>{
     
	@Query(value = "SELECT * FROM auxiliares WHERE idorigen=?1 AND idgrupo=?2 AND idsocio=?3 AND idproducto=100",nativeQuery = true)
	public Auxiliar buscarPorOGSIdproductoSocial(Integer idorigen,Integer idgrupo,Integer idsocio);
	
	@Query(value = "SELECT * FROM auxiliares a INNER JOIN productos_banca_movil pm USING(idproducto) INNER JOIN productos p USING(idproducto) WHERE a.idorigen=?1 AND a.idgrupo=?2 AND a.idsocio=?3 AND p.tipoproducto=0 AND a.estatus=2",nativeQuery = true)
	public List<Auxiliar>listaAhorrosActivos(Integer idorigen,Integer idgrupo,Integer idsocio,Pageable pageable);
	
	@Query(value = "SELECT a.* FROM auxiliares a INNER JOIN productos p USING(idproducto) WHERE a.idorigen=?1 AND a.idgrupo=?2 AND a.idsocio=?3	AND p.tipoproducto=1 AND a.estatus=2",nativeQuery = true)
    public List<Auxiliar>listaInversiones(Integer idorigen,Integer idgrupo,Integer idsocio,Pageable pageable);
	
	@Query(value = "SELECT a.* FROM auxiliares a INNER JOIN productos p USING(idproducto) WHERE a.idorigen=?1 AND a.idgrupo=?2 AND a.idsocio=?3 AND p.tipoproducto=2 AND a.estatus=2",nativeQuery = true)
    public List<Auxiliar>listaPrestamosActivos(Integer idorigen,Integer idgrupo,Integer idsocio,Pageable pageable);

	@Query(value="SELECT (DATE(a.fechaactivacion + int4(a.plazo)))  FROM auxiliares a WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3",nativeQuery = true)
	public Date fechaVencimientoAmortizacion(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	@Query(value = "SELECT count(*) FROM auxiliares a INNER JOIN productos p USING(idproducto) WHERE a.idorigen=?1 AND a.idgrupo=?2 AND a.idsocio=?3 AND p.tipoproducto=0 AND a.estatus=2",nativeQuery = true)
    public Integer contadorAhorros(Integer idorigen,Integer idgrupo,Integer idsocio);
	
	@Query(value = "SELECT count(*) FROM auxiliares a INNER JOIN productos p USING(idproducto) WHERE a.idorigen=?1 AND a.idgrupo=?2 AND a.idsocio=?3 AND p.tipoproducto=1 AND a.estatus=2",nativeQuery = true)
    public Integer contadorInversiones(Integer idorigen,Integer idgrupo,Integer idsocio);
	
	@Query(value = "SELECT count(*) FROM auxiliares a INNER JOIN productos p USING(idproducto) WHERE a.idorigen=?1 AND a.idgrupo=?2 AND a.idsocio=?3 AND p.tipoproducto=2 AND a.estatus=2",nativeQuery = true)
    public Integer contadorCreditos(Integer idorigen,Integer idgrupo,Integer idsocio);
	
	@Query(value = "SELECT * FROM auxiliares WHERE idorigen = ?1 AND idgrupo = ?2 AND idsocio = ?3 AND idproducto = ?4",nativeQuery = true)
	public Auxiliar buscarProductoSpei(Integer idOrigen,Integer idGrupo,Integer idSocio,Integer idProducto);
	
}
