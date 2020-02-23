INSERT INTO `usuarios`(user, password, enabled, nombre, apellido, email) VALUES('sergio','$2a$10$r4e70PMKKpwKOf8Dyrv3xO6JpH0mupk7crdKjTP85rIhqGp/UBiKW',1,'Sergio','Peña','sergio@hmail.com');     
INSERT INTO `usuarios`(user, password, enabled, nombre, apellido, email) VALUES('admin','$2a$10$BT4N8Ficxv2KmXrczEFF2.4vf88.cInceKTUA7FbEzmdPFLj32U/q',1,'Jhon','Doe','jhon.doe@admin.com');

INSERT INTO `roles`(nombre) VALUES('ROLE_USER');
INSERT INTO `roles`(nombre) VALUES('ROLE_ADMIN');

INSERT INTO `usuarios_roles`(usuario_id, rol_id) VALUES(1, 1);
INSERT INTO `usuarios_roles`(usuario_id, rol_id) VALUES(2, 2);
INSERT INTO `usuarios_roles`(usuario_id, rol_id) VALUES(2, 1);      