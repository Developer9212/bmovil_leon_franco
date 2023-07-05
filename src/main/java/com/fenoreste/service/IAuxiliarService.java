package com.fenoreste.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fenoreste.entity.Auxiliar;
import com.fenoreste.entity.AuxiliarPK;
import com.fenoreste.entity.PersonaPK;

public interface IAuxiliarService {

	public Auxiliar buscarPorId(AuxiliarPK pk);
	public Auxiliar buscarPorOgsIdproductoSocial(Integer idorigen,Integer idgrupo,Integer idsocio);
	public Auxiliar buscarPorOgsIdproducto(Integer idorigen,Integer idgrupo,Integer idsocio,Integer idproducto);
	public Date fechaVencimientoAmortizacion(AuxiliarPK pk);
	public List<Auxiliar> listaAhorros(PersonaPK pk,Pageable pageable);
	public List<Auxiliar> listaInversiones(PersonaPK pk,Pageable pageable);
	public List<Auxiliar> listaPrestamos(PersonaPK pk,Pageable pageable);
	public int contadorProductos(PersonaPK pk,Integer id);//(id=1 --- Ahorros,id=2 --- Inversion , id=3 --- Creditos)
}
