INSERT IGNORE INTO usuarios (id, username, password, rol, nombre) VALUES
(1, 'admin', '$2a$10$lnbDTAROW4BPyXGaMCzDNeRLr8JO4hP3jbH18A.apgGMnkD9xRSyi', 'ADMINISTRADOR', 'Administrador'),
(2, 'medico1', '$2a$10$lnbDTAROW4BPyXGaMCzDNeRLr8JO4hP3jbH18A.apgGMnkD9xRSyi', 'MEDICO', 'Ana Martinez'),
(4, 'paciente1', '$2a$10$lnbDTAROW4BPyXGaMCzDNeRLr8JO4hP3jbH18A.apgGMnkD9xRSyi', 'PACIENTE', 'Laura Torres'),


INSERT IGNORE INTO medicos (id, nombre, especialidad, usuario) VALUES
(1, 'Ana Martinez', 'Medicina general', 'medico1'),

INSERT IGNORE INTO consultas (id, nombre_paciente, motivo, consultorio, hora_inicio, hora_fin, medico_id, usuario_paciente) VALUES
(1, 'Laura Torres', 'Dolor de cabeza frecuente', 101, '08:00', '08:30', 1, 'paciente1'),
(3, 'Laura Torres', 'Revision de examenes', 102, '10:00', '10:30', 2, 'paciente1');
