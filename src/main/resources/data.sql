INSERT INTO Materiales (tipo_material, precio_por_kg)
VALUES ('carton', 0.12),
       ('papelBlanco', 0.27),
       ('petCristal', 0.61),
       ('aluminioLatas', 1.23),
       ('cobre', 8.00),
       ('chatarraFierro', 0.16),
       ('vidrio', 0.05),
       ('polietilenoAltaDensidad', 0.63),
       ('polietilenoBajaDensidad', 0.29),
       ('polipropileno', 0.23),
       ('cementoGris', 0.15),
       ('arenaConstruccion', 0.04),
       ('gravaConstruccion', 0.04),
       ('yeso', 1.25),
       ('aceroInoxidable', 0.60),
       ('bronce', 5.30),
       ('telaAlgodon', 16.50),
       ('telaPoliester', 10.40),
       ('telaLana', 10.50),
       ('seda', 30.00);

INSERT INTO entregas (cantidad_kg, fecha_entrega, nombre_proveedor, compensacion, id_material)
VALUES (45.50, NOW(), 'Recicladora El Progreso', 5.46, 1),    -- carton
       (30.00, NOW(), 'Papeles del Norte', 8.10, 2),          -- papelBlanco
       (15.75, NOW(), 'Reciclajes San Juan', 9.61, 3),        -- petCristal
       (22.00, NOW(), 'Metal Reusado GT', 27.06, 4),          -- aluminioLatas
       (5.00, NOW(), 'Chatarra y Cobre S.A.', 40.00, 5),      -- cobre
       (120.00, NOW(), 'Hierros La Fe', 19.20, 6),            -- chatarraFierro
       (50.00, NOW(), 'Vidrios Reciclados GT', 2.50, 7),      -- vidrio
       (8.50, NOW(), 'EcoPlast Alta', 5.36, 8),               -- polietilenoAltaDensidad
       (10.00, NOW(), 'ReciclaFácil', 2.90, 9),               -- polietilenoBajaDensidad
       (9.00, NOW(), 'EcoPolímeros', 2.07, 10),               -- polipropileno
       (300.00, NOW(), 'Construmateriales GT', 45.00, 11),    -- cementoGris
       (500.00, NOW(), 'Materiales y Arena S.A.', 20.00, 12), -- arenaConstruccion
       (450.00, NOW(), 'Transportes Grava y Más', 18.00, 13), -- gravaConstruccion
       (25.00, NOW(), 'Yesos de Occidente', 31.25, 14),       -- yeso
       (12.00, NOW(), 'Metales Inoxidables GT', 7.20, 15),    -- aceroInoxidable
       (7.00, NOW(), 'BronceArte', 37.10, 16),                -- bronce
       (2.50, NOW(), 'Textiles San Pablo', 41.25, 17),        -- telaAlgodon
       (3.20, NOW(), 'Recitex GT', 33.28, 18),                -- telaPoliester
       (1.80, NOW(), 'Fibras Naturales', 18.90, 19),          -- telaLana
       (0.75, NOW(), 'Sedas Premium', 22.50, 20); -- seda

INSERT INTO Administradores (nombre, correo_electronico, contrasena)
VALUES ('Juan Pérez', 'juan.perez@email.com', 'hash_contrasena_1'),
       ('Ana López', 'ana.lopez@email.com', 'hash_contrasena_2'),
       ('Carlos García', 'carlos.garcia@email.com', 'hash_contrasena_3'),
       ('María Hernández', 'maria.hernandez@email.com', 'hash_contrasena_4'),
       ('José Martínez', 'jose.martinez@email.com', 'hash_contrasena_5'),
       ('Laura Sánchez', 'laura.sanchez@email.com', 'hash_contrasena_6'),
       ('David González', 'david.gonzalez@email.com', 'hash_contrasena_7'),
       ('Sofía Rodríguez', 'sofia.rodriguez@email.com', 'hash_contrasena_8'),
       ('Miguel Fernández', 'miguel.fernandez@email.com', 'hash_contrasena_9'),
       ('Lucía Díaz', 'lucia.diaz@email.com', 'hash_contrasena_10');