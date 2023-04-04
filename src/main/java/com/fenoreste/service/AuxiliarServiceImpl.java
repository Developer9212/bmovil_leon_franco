package com.fenoreste.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.AuxiliarDao;
import com.fenoreste.entity.Auxiliar;
import com.fenoreste.entity.AuxiliarPK;
import com.fenoreste.entity.PersonaPK;

@Service
public class AuxiliarServiceImpl implements IAuxiliarService{
    
	@Autowired
	private AuxiliarDao auxiliarDao;
	
	@Override
	public Auxiliar buscarPorId(AuxiliarPK pk) {
		return auxiliarDao.findById(pk).orElse(null);
	}

	@Override
	public Auxiliar buscarPorOgsIdproductoSocial(Integer idorigen, Integer idgrupo, Integer idsocio) {
		return auxiliarDao.buscarPorOGSIdproductoSocial(idorigen, idgrupo, idsocio);
	}	

	@Override
	public Date fechaVencimientoAmortizacion(AuxiliarPK pk) {
		return auxiliarDao.fechaVencimientoAmortizacion(pk.getIdorigenp(),pk.getIdproducto(),pk.getIdauxiliar());
	}

	@Override
	public List<Auxiliar> listaAhorros(PersonaPK pk,Pageable pageable) {
		return auxiliarDao.listaAhorrosActivos(pk.getIdorigen().intValue(),pk.getIdgrupo(),pk.getIdsocio(),pageable);
	}

	@Override
	public List<Auxiliar> listaInversiones(PersonaPK pk,Pageable pageable) {
		return auxiliarDao.listaInversiones(pk.getIdorigen().intValue(),pk.getIdgrupo().intValue(),pk.getIdsocio().intValue(),pageable);
	}

	@Override
	public List<Auxiliar> listaPrestamos(PersonaPK pk,Pageable pageable) {
		return auxiliarDao.listaPrestamosActivos(pk.getIdorigen(),pk.getIdgrupo(),pk.getIdsocio(),pageable);
	}

	@Override
	public int contadorProductos(PersonaPK pk,Integer id) {
		int conta = 0;
		
		switch (id) {
		case 1:
		    conta = auxiliarDao.contadorAhorros(pk.getIdorigen(), pk.getIdgrupo(), pk.getIdsocio());	
			break;
		case 2:
			conta = auxiliarDao.contadorInversiones(pk.getIdorigen(), pk.getIdgrupo(), pk.getIdsocio());
			break;
		case 3:
			conta = auxiliarDao.contadorCreditos(pk.getIdorigen(), pk.getIdgrupo(), pk.getIdsocio());
			break;
		}

		return conta;
	}

}
