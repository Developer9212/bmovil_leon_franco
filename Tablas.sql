DROP TABLE IF EXISTS users;
CREATE TABLE users(id integer,username varchar(45),password text,create_at date);


 /*PRODUCTOS QUE OPERAN BANCA MOVIL*/
DROP table IF EXISTS productos_banca_movil;
CREATE table productos_banca_movil(
   idproducto integer,
   tipoproducto integer,
   nombreproducto text,
   consulta boolean default true,
   deposito boolean default true,
   retiro boolean default true,
   primary key(idproducto)

);

INSERT INTO productos_banca_movil VALUES(131,0,'AHORRO CUENTA CONFIANZA',true,true,false);
INSERT INTO productos_banca_movil VALUES(130,0,'AHORRO CUENTA CORRIENTE',true,true,false);
INSERT INTO productos_banca_movil VALUES(170,0,'AHORRO JOVEN',true,true,false);
INSERT INTO productos_banca_movil VALUES(111,0,'AHORRO ADICIONAL',true,true,false);
INSERT INTO productos_banca_movil VALUES(110,0,'AHORRO ADULTO',true,true,false);
INSERT INTO productos_banca_movil VALUES(202,1,'Deposito a plazo 90 dias',true,false,false);
INSERT INTO productos_banca_movil VALUES(203,1,'Deposito a plazo fijo 180 dias',true,false,false);
INSERT INTO productos_banca_movil VALUES(201,1,'Deposito a Plazo 60 dias',true,false,false);
INSERT INTO productos_banca_movil VALUES(200,1,'Deposito a plazo 30 dias',true,false,false);
INSERT INTO productos_banca_movil VALUES(31603,2,'PRESTAMO MAXIMO VIVIENDA',true,true,false);
INSERT INTO productos_banca_movil VALUES(31702,2,'PRESTAMO 31702 GAR',true,true,false);
INSERT INTO productos_banca_movil VALUES(31802,2,'PRESTAMO 31802 GAR',true,true,false);
INSERT INTO productos_banca_movil VALUES(31502,2,'PRESTAMO 31502 GAR',true,true,false);
INSERT INTO productos_banca_movil VALUES(31602,2,'PRESTAMO 31602 GAR',true,true,false);
INSERT INTO productos_banca_movil VALUES(32302,2,'CREDI-ECO',true,true,false);
INSERT INTO productos_banca_movil VALUES(30000,2,'PRESTAMO PARA PRUEBAS',true,true,false);
INSERT INTO productos_banca_movil VALUES(30312,2,'PRESTAMO AUTOMOVIL ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30412,2,'PRESTAMO CONFIANZA ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30512,2,'PRESTAMO ADICIONAL ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31523,2,'PRESTAMO ORDINARIO VIVIENDA ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30522,2,'PRESTAMO ADICIONAL ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31613,2,'PRESTAMO MAXIMO VIVIENDA ( Renovado ) ',true,true,false);
INSERT INTO productos_banca_movil VALUES(31902,2,'PRESTAMO MOTOCICLETA',true,true,false);
INSERT INTO productos_banca_movil VALUES(31112,2,'PRESTAMO ADICIONAL COMPUTADORA ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31212,2,'PRESTAMO PREAUTORIZADO ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31312,2,'PRESTAMO PREAUTORIZADO SIN GAR ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31503,2,'PRESTAMO ORDINARIO VIVIENDA',true,true,false);
INSERT INTO productos_banca_movil VALUES(30102,2,'ORDINARIO INSOLUTO ( Normal ) Consumo',true,true,false);
INSERT INTO productos_banca_movil VALUES(31322,2,'PRESTAMO PREAUTORIZADO SIN GAR ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31623,2,'PRESTAMO MAXIMO VIVIENDA ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30812,2,'PRESTAMO HIPOTECARIO SOBRE SAL ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30212,2,'PREAUTORIZADO CREDI CUMPLIDO ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31122,2,'PRESTAMO ADICIONAL COMPUTADORA ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31222,2,'PRESTAMO PREAUTORIZADO ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30612,2,'PRESTAMO MAXIMO ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30622,2,'PRESTAMO MAXIMO ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30722,2,'CREDITARJETA ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31002,2,'PRESTAMO ORDINARIO',true,true,false);
INSERT INTO productos_banca_movil VALUES(31713,2,'PRESTAMO HIPOTECARIO VIVIENDA ( Renovado ) ',true,true,false);
INSERT INTO productos_banca_movil VALUES(31723,2,'PRESTAMO HIPOTECARIO VIVIENDA ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30712,2,'CREDITARJETA ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30912,2,'PRESTAMO MAXIMO ESPECIAL ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31012,2,'PRESTAMO ORDINARIO ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30922,2,'PRESTAMO MAXIMO ESPECIAL ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31022,2,'PRESTAMO ORDINARIO ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30222,2,'PREAUTORIZADO CREDI CUMPLIDO ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30322,2,'PRESTAMO AUTOMOVIL ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30422,2,'PRESTAMO CONFIANZA ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(30122,2,'ORDINARIO INSOLUTO ( Reestructurado ) Consumo',true,true,false);
INSERT INTO productos_banca_movil VALUES(30822,2,'PRESTAMO HIPOTECARIO SOBRE SAL ( Reestructurado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31412,2,'PRESTAMO ADICIONAL ESCOLAR ( Renovado )',true,true,false);
INSERT INTO productos_banca_movil VALUES(31513,2,'PRESTAMO ORDINARIO VIVIENDA ( Renovado ) ',true,true,false);
INSERT INTO productos_banca_movil VALUES(30112,2,'ORDINARIO INSOLUTO ( Renovado ) Consumo',true,true,false);
INSERT INTO productos_banca_movil VALUES(31102,2,'PRESTAMO ADICIONAL COMPUTADORA',true,true,false);
INSERT INTO productos_banca_movil VALUES(31202,2,'PRESTAMO PREAUTORIZADO',true,true,false);
INSERT INTO productos_banca_movil VALUES(33102,2,'CREDITO REVOLVENTE',true,true,false);
INSERT INTO productos_banca_movil VALUES(31703,2,'PRESTAMO HIPOTECARIO SOBRE SAL VIVIENDA',true,true,false);
INSERT INTO productos_banca_movil VALUES(30802,2,'PRESTAMO HIPOTECARIO SOBRE SAL',true,true,false);
INSERT INTO productos_banca_movil VALUES(32102,2,'PRESTAMO CREDINOMINA',true,true,false);
INSERT INTO productos_banca_movil VALUES(30702,2,'CREDITARJETA',true,true,false);
INSERT INTO productos_banca_movil VALUES(30202,2,'PREAUTORIZADO CREDI CUMPLIDO',true,true,false);
INSERT INTO productos_banca_movil VALUES(31302,2,'PRESTAMO PREAUTORIZADO SIN GAR',true,true,false);
INSERT INTO productos_banca_movil VALUES(30302,2,'PRESTAMO AUTOMOVIL',true,true,false);
INSERT INTO productos_banca_movil VALUES(30402,2,'PRESTAMO CONFIANZA',true,true,false);
INSERT INTO productos_banca_movil VALUES(31402,2,'PRESTAMO ADICIONAL ESCOLAR',true,true,false);
INSERT INTO productos_banca_movil VALUES(30602,2,'PRESTAMO MAXIMO',true,true,false);
INSERT INTO productos_banca_movil VALUES(30902,2,'PRESTAMO MAXIMO ESPECIAL',true,true,false);
INSERT INTO productos_banca_movil VALUES(32202,2,'CREDIJOVEN',true,true,false);
INSERT INTO productos_banca_movil VALUES(31612,2,'PRESTAMO APOYO COVID-19',true,true,false);
INSERT INTO productos_banca_movil VALUES(30502,2,'PRESTAMO ADICIONAL',true,true,false);


DROP table IF EXISTS ws_clabe_respuesta_alianza;
CREATE table ws_clabe_respuesta_alianza(
    idoperacion integer,
    descripcion varchar(45),
    fecha timestamp,
    clabe varchar(45),
    idorigenp integer,
    idproducto integer,
    idauxiliar integer,
    codigohttp integer,
    mensajerespuesta text
);



