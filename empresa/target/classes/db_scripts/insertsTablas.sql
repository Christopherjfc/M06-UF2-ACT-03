-- Insertando departamentos
INSERT INTO Departament (nomDepartament) VALUES 
('Recursos Humanos'),
('Desarrollo'),
('Ventas'),
('Marketing'),
('Soporte Técnico');

-- Insertando empleados
INSERT INTO Empleat (nomEmpleat, dni, correu, telefon, idDepartament) VALUES 
('Juan Pérez', '12345678A', 'juan.perez@email.com', '654321987', 1),
('María Gómez', '87654321B', 'maria.gomez@email.com', '612345678', 2),
('Carlos Ruiz', '45678912C', 'carlos.ruiz@email.com', '698745632', 3),
('Ana Fernández', '78912345D', 'ana.fernandez@email.com', '678954321', 4),
('Luis Martínez', '32165498E', 'luis.martinez@email.com', '612398765', 5),
('Elena Sánchez', '85296374F', 'elena.sanchez@email.com', '632154789', 2),
('Javier Torres', '36985214G', 'javier.torres@email.com', '654987321', 3);

-- Insertando tareas
INSERT INTO Tasca (nomTasca, descripcio) VALUES 
('Análisis de Requisitos', 'Definir requisitos funcionales y no funcionales'),
('Diseño de Base de Datos', 'Crear el modelo de datos'),
('Desarrollo Backend', 'Implementar la API y lógica de negocio'),
('Diseño Frontend', 'Crear la interfaz de usuario con React'),
('Testing y QA', 'Realizar pruebas unitarias y de integración'),
('Soporte Post-Producción', 'Atender incidencias y optimizar el sistema'),
('Estrategia de Marketing', 'Diseñar una campaña de marketing digital');

-- Insertando historial (Historic)
INSERT INTO Historic (dataInici, dataFi, idTasca) VALUES 
('2024-02-01', '2024-02-10', 1),
('2024-02-05', '2024-02-15', 2),
('2024-02-10', '2024-02-20', 3),
('2024-02-15', '2024-02-25', 4),
('2024-03-01', '2024-03-10', 5),
('2024-03-05', '2024-03-15', 6),
('2024-03-10', '2024-03-20', 7);
