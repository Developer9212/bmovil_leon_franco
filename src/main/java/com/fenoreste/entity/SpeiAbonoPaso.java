package com.fenoreste.entity;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "spei_entrada_temporal_cola_guardado")
@Data
public class SpeiAbonoPaso {

	@EmbeddedId
	private AbonoSpeiPK pk;
	private Integer id;
	private boolean aplicado;
	private Integer idorigen;
	private Integer idgrupo;
	private Integer idsocio;
	private Integer idorigenp = 0;
	private Integer idproducto = 0;
	private Integer idauxiliar = 0;
	private boolean esentrada;
	private Double acapital;
	private Double io_pag = 0.0;
	private Double io_cal = 0.0;
	private Double im_pag = 0.0;
	private Double im_cal = 0.0;
	private Double aiva = 0.0;
	private Double saldodiacum = 0.0;
	private Double abonifio = 0.0;
	private String idcuenta = "0";
	private Double ivaio_pag = 0.0;
	private Double ivaio_cal = 0.0;
	private Double ivaim_pag = 0.0;
	private Double ivaim_cal = 0.0;	
	private Integer tipomov = 0;
	private Integer diasvencidos = 0;
	private Double montovencido = 0.0;
	private Integer idorigena = 0;
	private boolean huella_valida;
	private String concepto_mov;
	private String fe_nom_archivo;
	private String fe_xml;
	private String sai_aux = "";
	private String poliza_generada;
	private Date fecha_aplicado;
	private Integer tipopoliza;
}
