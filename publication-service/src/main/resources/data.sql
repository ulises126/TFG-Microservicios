
INSERT INTO titulaciones(id, nombre) VALUES (1, 'Grado en Ingeniería Informática');
INSERT INTO asignaturas(id, nombre, curso, titulacion_id) VALUES (1, 'Matemáticas II', 1, 1);
INSERT INTO asignaturas(id, nombre, curso, titulacion_id) VALUES (2, 'Fisica', 1, 1);

INSERT INTO publicaciones
(id,
descripcion,
fecha_publicacion,
modalidad,
precio_hora,
titulo,
asignatura_id,
status)
VALUES
(1,
'Clases de Matlab para Matemáticas II.',
'2022-10-10',
'PRESENCIAL',
5,
'Doy clases online de Matlab para matemáticas 2.',
1,
'CREATED');