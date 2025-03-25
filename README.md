Reto en grupo de CIC Gestión de Colección Personal

Descripción:
Desarrollo de una aplicación web para gestionar una colección personal de música, libros y películas, permitiendo controlar su ubicación y su préstamo.

Requisitos:

Crear Item: Permitir crear nuevos ítems especificando título, tipo, formato, ubicación y fecha de adquisición
Modificar item: Permitir modificar datos de ítems existentes excepto su estado
Eliminar items: Permitir eliminar items sin préstamos activos.
Buscar items: Permitir búsqueda por título, tipo, estado y ubicación con resultados paginados
Ordenar items: Permitir ordenar listados de ítems por diferentes criterios
Registrar prestamos: Permitir registrar préstamo de ítems disponibles con persona y fecha prevista devolución
Registar devolucion: Permitir registrar la devolución de items prestados
Listar Prestamos: Mostrar listado de préstamos activos con posibilidad de filtrado por persona y fechas
Gestionar Tipos: Permitir mantenimiento (CRUD) de tipos de ítem
Gestionar formatos: Permitir mantenimiento (CRUD) de formatos según tipo
Control de estados: Gestionar automáticamente los cambios de estado (Disponible/Prestado)


Para levantar la aplicación:

Levantar front : ng serve
Servidor de back: mvn spring-boot:run
