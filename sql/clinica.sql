CREATE DATABASE IF NOT EXISTS clinica;
USE clinica;

CREATE TABLE IF NOT EXISTS usuarios (
  id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(20) NOT NULL,
  password VARCHAR(255) NOT NULL,
  rol VARCHAR(20),
  nombre VARCHAR(50),
  PRIMARY KEY (id),
  UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS medicos (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  especialidad VARCHAR(50),
  usuario VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (usuario)
);

CREATE TABLE IF NOT EXISTS consultas (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre_paciente VARCHAR(40) NOT NULL,
  motivo VARCHAR(100),
  consultorio INT,
  hora_inicio VARCHAR(255),
  hora_fin VARCHAR(255),
  medico_id BIGINT,
  usuario_paciente VARCHAR(20),
  PRIMARY KEY (id),
  FOREIGN KEY (medico_id) REFERENCES medicos (id)
);

INSERT IGNORE INTO usuarios (id, username, password, rol, nombre) VALUES
(1, 'admin', '$2a$10$lnbDTAROW4BPyXGaMCzDNeRLr8JO4hP3jbH18A.apgGMnkD9xRSyi', 'ADMINISTRADOR', 'Administrador'),
(2, 'medico1', '$2a$10$lnbDTAROW4BPyXGaMCzDNeRLr8JO4hP3jbH18A.apgGMnkD9xRSyi', 'MEDICO', 'Ana Martinez'),
(3, 'medico2', '$2a$10$lnbDTAROW4BPyXGaMCzDNeRLr8JO4hP3jbH18A.apgGMnkD9xRSyi', 'MEDICO', 'Carlos Ramirez'),
(4, 'paciente1', '$2a$10$lnbDTAROW4BPyXGaMCzDNeRLr8JO4hP3jbH18A.apgGMnkD9xRSyi', 'PACIENTE', 'Laura Torres'),
(5, 'paciente2', '$2a$10$lnbDTAROW4BPyXGaMCzDNeRLr8JO4hP3jbH18A.apgGMnkD9xRSyi', 'PACIENTE', 'Wilfredo Sanchez');

INSERT IGNORE INTO medicos (id, nombre, especialidad, usuario) VALUES
(1, 'Ana Martinez', 'Medicina general', 'medico1'),
(2, 'Carlos Ramirez', 'Pediatria', 'medico2');

INSERT IGNORE INTO consultas (id, nombre_paciente, motivo, consultorio, hora_inicio, hora_fin, medico_id, usuario_paciente) VALUES
(1, 'Laura Torres', 'Dolor de cabeza frecuente', 101, '08:00', '08:30', 1, 'paciente1'),
(2, 'Wilfredo Sanchez', 'Chequeo general', 101, '09:00', '09:30', 1, 'paciente2'),
(3, 'Laura Torres', 'Revision de examenes', 102, '10:00', '10:30', 2, 'paciente1');

SELECT * FROM usuarios;
SELECT * FROM medicos;
SELECT * FROM consultas;
