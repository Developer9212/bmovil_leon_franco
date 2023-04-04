package com.fenoreste.utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HerramientasUtil {

	public Ogs ogs(String customerId) {
		Ogs ogs = new Ogs();
		try {
			ogs.setIdorigen(Integer.parseInt(customerId.substring(0, 6)));
			ogs.setIdgrupo(Integer.parseInt(customerId.substring(6, 8)));
			ogs.setIdsocio(Integer.parseInt(customerId.substring(8, 14)));	
		} catch (Exception e) {
			System.out.println("Error al convertir formato ogs:"+e.getMessage());
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
			System.out.println("Error al convertir formato opa:"+e.getMessage());
		}		
	 return opa;
	}
	
	
	public String convertFechaDate(Date date) {
		String fecha = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fecha = sdf.format(date)+"T00:00:00";
		} catch (Exception e) {
			log.info("Error al convertir fecha:"+e.getMessage());
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
