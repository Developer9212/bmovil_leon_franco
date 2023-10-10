
DELETE FROM tablas WHERE idtabla = 'banca_movil' AND idelemento = 'user_ws_token';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1,dato2)VALUES('banca_movil','user_ws_token','Usuario que se utiliza para generar token','test','test');

DELETE FROM tablas WHERE idtabla='banca_movil' AND idelemento = 'usuario';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1)VALUES('banca_movil','usuario','Usuario para operar banca movil','999');

DELETE FROM tablas WHERE idtabla='banca_movil' AND idelemento = 'credenciales_token';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1,dato2)VALUES('banca_movil','credenciales_token','Credenciales para formar token','leonfranco-cmascore-dev','Q1QNviHK3');

DELETE FROM tablas WHERE idtabla='banca_movil' AND idelemento = 'producto_clabe';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1)VALUES('banca_movil','producto_clabe','Producto asociado a a clabe stp','110');

DELETE FROM tablas WHERE idtabla = 'banca_movil' AND idelemento = 'prefijo_concepto_poliza';
INSERT INTO tablas(idtabla,idelemento,dato2) values('banca_movil','prefijo_concepto_poliza','Transferencia banca movil');

DELETE FROM tablas WHERE idtabla = 'banca_movil' AND idelemento = 'horario_actividad';
INSERT INTO tablas(idtabla,idelemento,dato1,dato2) values('banca_movil','horario_actividad','08:00','19:00');



/*DATOS PARA SPEI*/
DELETE FROM tablas WHERE idtabla='spei' AND idelemento = 'usuario_abono';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1)VALUES('spei','usuario_abono','Usuario para operar spei entrada','104');

DELETE FROM tablas WHERE idtabla='spei' AND idelemento = 'cuenta_abono';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1)VALUES('spei','cuenta_abono','Cuenta contable para balance','20407160104002');

DELETE FROM tablas WHERE idtabla='spei' AND idelemento = 'cuenta_comision_abono';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1)VALUES('spei','cuenta_comision_abono','Cuenta contable a donde va la comision','20407160104002');


DELETE FROM tablas WHERE idtabla='spei' AND idelemento = 'usuario_dispersion';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1)VALUES('spei','usuario_dispersion','Usuario para operar spei salida','102');

DELETE FROM tablas WHERE idtabla='spei' AND idelemento = 'cuenta_dispersion';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1)VALUES('spei','cuenta_dispersion','Cuenta contable para balance','20407160104002');

DELETE FROM tablas WHERE idtabla='spei' AND idelemento = 'cuenta_comision_dispersion';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1)VALUES('spei','cuenta_comision_dispersion','Cuenta contable a donde va la comision','20407160104002');



