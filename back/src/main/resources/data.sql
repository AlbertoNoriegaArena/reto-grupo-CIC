-- Insertar Tipos de Item
INSERT INTO tipo_item (id, nombre) VALUES
(1, 'Musica'),
(2, 'Libro'),
(3, 'Pelicula')
ON CONFLICT (id) DO NOTHING; -- O ajusta según tu BD si necesitas manejar conflictos

-- Insertar Formatos
INSERT INTO formato (id, nombre) VALUES
(1, 'CD'),
(2, 'Cassette'),
(3, 'Vinilo'),
(4, 'VHS'),
(5, 'DVD'),
(6, 'Blu-ray'),
(7, 'Fisico'),
(8, 'Digital')
ON CONFLICT (id) DO NOTHING;

-- Insertar Relaciones TipoItemFormato
INSERT INTO tipo_item_formato (tipo_item_id, formato_id) VALUES
(1, 1), (1, 2), (1, 3), -- Musica: CD, Cassette, Vinilo
(3, 5), (3, 6), (3, 4), -- Pelicula: DVD, Blu-ray, VHS
(2, 7), (2, 8)          -- Libro: Fisico, Digital
ON CONFLICT (tipo_item_id, formato_id) DO NOTHING;

-- Insertar Items (Asumiendo IDs 1-15)
-- Nota: El estado se actualizará a PRESTADO para los items que se presten más abajo
INSERT INTO item (id, nombre, tipo_id, formato_id, ubicacion, fecha, estado) VALUES
-- Musica (IDs 1-5)
(1, 'Thriller - Michael Jackson', 1, 1, 'Estante B', '1982-11-30', 'DISPONIBLE'),
(2, 'Bohemian Rhapsody - Queen', 1, 3, 'Estante C', '1975-10-31', 'DISPONIBLE'),
(3, 'The Dark Side of the Moon - Pink Floyd', 1, 2, 'Estante C', '1973-03-01', 'DISPONIBLE'),
(4, 'Back in Black - AC/DC', 1, 3, 'Estante A', '1980-07-25', 'DISPONIBLE'),
(5, 'Nevermind - Nirvana', 1, 1, 'Estante B', '1991-09-24', 'DISPONIBLE'),
-- Peliculas (IDs 6-10)
(6, 'Interstellar', 3, 5, 'Estante C', '2014-10-26', 'DISPONIBLE'),
(7, 'Matrix', 3, 6, 'Estante A', '1999-03-31', 'DISPONIBLE'),
(8, 'El silencio de los corderos', 3, 4, 'Estante A', '1991-02-14', 'DISPONIBLE'),
(9, 'El Padrino', 3, 5, 'Estante B', '1972-03-24', 'DISPONIBLE'),
(10, 'Pulp Fiction', 3, 6, 'Estante C', '1994-10-14', 'DISPONIBLE'),
-- Libros (IDs 11-15)
(11, 'El Señor de los Anillos', 2, 7, 'Estante A', '1954-07-29', 'DISPONIBLE'),
(12, '1984 - George Orwell', 2, 8, 'Online', '1949-06-08', 'DISPONIBLE'),
(13, 'Cien años de soledad', 2, 7, 'Estante B', '1967-05-30', 'DISPONIBLE'),
(14, 'El Hobbit', 2, 7, 'Estante B', '1937-09-21', 'DISPONIBLE'),
(15, 'Crimen y Castigo', 2, 7, 'Estante C', '1866-01-01', 'DISPONIBLE')
ON CONFLICT (id) DO NOTHING;

-- Insertar Musica (Usando los IDs de Item 1-5)
INSERT INTO musica (item_id, cantante, genero, album, duracion) VALUES
(1, 'Michael Jackson', 'Pop', 'Thriller', '42:19'),
(2, 'Queen', 'Rock', 'A Night at the Opera', '5:55'),
(3, 'Pink Floyd', 'Rock Progresivo', 'The Dark Side of the Moon', '42:50'),
(4, 'AC/DC', 'Hard Rock', 'Back in Black', '42:11'),
(5, 'Nirvana', 'Grunge', 'Nevermind', '49:20')
ON CONFLICT (item_id) DO NOTHING;

-- Insertar Peliculas (Usando los IDs de Item 6-10)
INSERT INTO pelicula (item_id, director, genero, duracion, fecha_estreno) VALUES
(6, 'Christopher Nolan', 'Ciencia Ficción', 169, '2014-11-07'),
(7, 'Lana Wachowski, Lilly Wachowski', 'Ciencia Ficción', 136, '1999-03-31'),
(8, 'Jonathan Demme', 'Thriller', 118, '1991-02-14'),
(9, 'Francis Ford Coppola', 'Drama', 175, '1972-03-24'),
(10, 'Quentin Tarantino', 'Crimen', 154, '1994-10-14')
ON CONFLICT (item_id) DO NOTHING;

-- Insertar Libros (Usando los IDs de Item 11-15)
INSERT INTO libro (item_id, autor, isbn, editorial, numero_paginas, fecha_publicacion) VALUES
(11, 'J.R.R. Tolkien', '978-84-450-7179-3', 'Minotauro', 1200, '1954-07-29'),
(12, 'George Orwell', '978-84-233-5070-0', 'Destino', 328, '1949-06-08'),
(13, 'Gabriel García Márquez', '978-84-376-0494-7', 'Cátedra', 471, '1967-05-30'),
(14, 'J.R.R. Tolkien', '978-84-450-7149-6', 'Minotauro', 310, '1937-09-21'),
(15, 'Fiódor Dostoyevski', '978-84-206-5727-2', 'Alianza', 672, '1866-01-01')
ON CONFLICT (item_id) DO NOTHING;

-- Insertar Personas (Asumiendo IDs 1-5)
INSERT INTO persona (id, nombre, direccion, email, telefono) VALUES
(1, 'Juan Pérez', 'Calle Mayor 1', 'juan@example.com', '123456789'),
(2, 'María López', 'Calle Sol 2', 'maria@example.com', '987654321'),
(3, 'Carlos García', 'Avenida del Sol 15', 'carlos@example.com', '654987321'),
(4, 'Laura Martínez', 'Plaza de la Luna 8', 'laura@example.com', '963852741'),
(5, 'Pedro Rodríguez', 'Calle Estrella 22', 'pedro@example.com', '666555444')
ON CONFLICT (id) DO NOTHING;

-- Insertar Prestamos (Asumiendo IDs 1-9)
-- Usamos CURRENT_DATE para la fecha de préstamo y calculamos la fecha prevista
INSERT INTO prestamo (id, item_id, persona_id, fecha_prestamo, fecha_devolucion, fecha_prevista_devolucion) VALUES
(1, 1, 1, CURRENT_DATE, NULL, CURRENT_DATE + INTERVAL '15 day'),
(2, 2, 2, CURRENT_DATE, NULL, CURRENT_DATE + INTERVAL '7 day'),
(3, 3, 3, CURRENT_DATE, NULL, CURRENT_DATE + INTERVAL '10 day'),
(4, 4, 4, CURRENT_DATE, NULL, CURRENT_DATE + INTERVAL '14 day'),
(5, 5, 1, CURRENT_DATE, NULL, CURRENT_DATE + INTERVAL '21 day'),
(6, 6, 1, CURRENT_DATE, NULL, CURRENT_DATE + INTERVAL '7 day'),
(7, 7, 2, CURRENT_DATE, NULL, CURRENT_DATE + INTERVAL '15 day'),
(8, 8, 2, CURRENT_DATE, NULL, CURRENT_DATE + INTERVAL '10 day'),
(9, 9, 4, CURRENT_DATE, NULL, CURRENT_DATE + INTERVAL '14 day')
-- (10, 10, 3, CURRENT_DATE, NULL, CURRENT_DATE + INTERVAL '21 day') -- Prestamo 10 estaba comentado
ON CONFLICT (id) DO NOTHING;

-- Actualizar el estado de los Items prestados
UPDATE item SET estado = 'PRESTADO' WHERE id IN (1, 2, 3, 4, 5, 6, 7, 8, 9);

