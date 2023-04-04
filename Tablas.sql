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


INSERT INTO productos_banca_movil VALUES(111,0,'AHORRO ADICIONAL');
INSERT INTO productos_banca_movil VALUES(110,0,'AHORRO ADULTO');