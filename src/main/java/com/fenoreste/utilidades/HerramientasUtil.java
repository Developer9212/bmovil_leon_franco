package com.fenoreste.utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HerramientasUtil {

	public Ogs ogs(String customerId) {
		log.info("OGS a formatear:"+customerId);
		Ogs ogs = new Ogs();
		try {
			ogs.setIdorigen(Integer.parseInt(customerId.substring(0, 6)));
			ogs.setIdgrupo(Integer.parseInt(customerId.substring(6, 8)));
			ogs.setIdsocio(Integer.parseInt(customerId.substring(8, 14)));	
		} catch (Exception e) {
			ogs.setIdorigen(0);
			ogs.setIdgrupo(0);
			ogs.setIdsocio(0);
			System.out.println("Error al convertir formato ogs:"+e.getMessage());
			return ogs;
		}
		return ogs;
	}

	public Opa opa(String productBankIdentifier) {
		Opa opa = new Opa();
		try {
			opa.setIdorigenp(Integer.parseInt(productBankIdentifier.substring(0, 6)));
			opa.setIdproducto(Integer.parseInt(productBankIdentifier.substring(6, 11)));
			opa.setIdauxiliar(Integer.parseInt(productBankIdentifier.substring(11, 19)));
		} catch (Exception e) {
			opa.setIdorigenp(0);
			opa.setIdproducto(0);
			opa.setIdauxiliar(0);
			System.out.println("Error al convertir formato opa:"+e.getMessage());
			return opa;			
		}		
	 return opa;
	}
	
	
	public String convertFechaDate(Date date) {
		String fecha = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fecha = sdf.format(date);
		} catch (Exception e) {
			log.info("Error al convertir fecha:"+e.getMessage());
		}
		return fecha;
	}
	
	//Convertimos una fecha a date y retornamos solo marcas de tiempos hh:mm:ss.S
	public String convertFechaDateHora(Date date) {
		String fecha = "";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			fecha = sdf.format(date);
		} catch (Exception e) {
			log.info("Error al convertir fecha Minutes:"+e.getMessage());
		}
		return fecha;
	}
	
	public Date convertFechaString(String date) {
		Date fecha = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fecha = sdf.parse(date);
		} catch (Exception e) {
			log.info("Error al convertir fecha:"+e.getMessage());
		}
		return fecha;
	}
	

}
