package com.fenoreste.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.AuxiliarDDao;
import com.fenoreste.entity.AuxiliarD;
import com.fenoreste.entity.AuxiliarPK;

@Service
public class AuxiliarDServiceImpl implements IAuxiliarDService{
    
	@Autowired
	private AuxiliarDDao auxiliarDDao;
	
	@Override
	public AuxiliarD buscarUltimoMovimiento(AuxiliarPK pk,Integer idusuario,Integer idtipo) {
		return auxiliarDDao.buscarUltimoMovimiento(pk.getIdorigenp(),pk.getIdproducto(),pk.getIdauxiliar(),idusuario,idtipo);
	}

	@Override
	public List<AuxiliarD> buscarTodosMovs(AuxiliarPK pk,Date fechaIni,Date fechaFin,Pageable pageable) {
		return auxiliarDDao.todosMovimientos(pk.getIdorigenp(),pk.getIdproducto(),pk.getIdauxiliar(),fechaIni,fechaFin,pageable);//todosMovimientos(pk.getIdorigenp(),pk.getIdproducto(),pk.getIdauxiliar());
	}

	@Override
	public Integer contadorPorFecha(AuxiliarPK pk, Date fechaIni, Date fechaFin) {
		return auxiliarDDao.totalMovFecha(pk.getIdorigenp(),pk.getIdproducto(),pk.getIdauxiliar(),fechaIni, fechaFin);
	}

	@Override
	public AuxiliarD buscarUltimoMovimiento(AuxiliarPK pk) {
		return auxiliarDDao.buscarUltimoMovimiento(pk.getIdorigenp(),pk.getIdproducto(), pk.getIdauxiliar());
	}

	

}
