
DELETE FROM tablas WHERE idtabla = 'banca_movil' AND idelemento = 'user_ws_token';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1,dato2)VALUES('banca_movil','user_ws_token','Usuario que se utiliza para generar token','test','test');

DELETE FROM tablas WHERE idtabla='banca_movil' AND idelemento = 'usuario';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1)VALUES('banca_movil','usuario','Usuario para operar banca movil','999');

DELETE FROM tablas WHERE idtabla='banca_movil' AND idelemento = 'credenciales_token';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1,dato2)VALUES('banca_movil','credenciales_token','Credenciales para formar token','leonfranco-cmascore-dev','Q1QNviHK3');
