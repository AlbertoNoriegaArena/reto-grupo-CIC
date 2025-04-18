package es.santander.ascender.retoGrupoCIC.service;

import es.santander.ascender.retoGrupoCIC.config.CustomValidationException;
import es.santander.ascender.retoGrupoCIC.config.FormatoNoValidoException;
import es.santander.ascender.retoGrupoCIC.config.FormatoNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.ItemAsociadoAPrestamoException;
import es.santander.ascender.retoGrupoCIC.config.ItemNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.ItemPrestadoException;
import es.santander.ascender.retoGrupoCIC.config.TipoItemNotFoundException;
import es.santander.ascender.retoGrupoCIC.dto.ItemDTO;
import es.santander.ascender.retoGrupoCIC.dto.ItemCompletoDTO;
import es.santander.ascender.retoGrupoCIC.model.EstadoItem;
import es.santander.ascender.retoGrupoCIC.model.Formato;
import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.model.Prestamo;
import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormato;
import es.santander.ascender.retoGrupoCIC.model.Libro;
import es.santander.ascender.retoGrupoCIC.model.Musica;
import es.santander.ascender.retoGrupoCIC.model.Pelicula;
import es.santander.ascender.retoGrupoCIC.repository.ItemRepository;
import es.santander.ascender.retoGrupoCIC.repository.PrestamoRepository;
import es.santander.ascender.retoGrupoCIC.repository.MusicaRepository;
import es.santander.ascender.retoGrupoCIC.repository.LibroRepository;
import es.santander.ascender.retoGrupoCIC.repository.PeliculaRepository;
import es.santander.ascender.retoGrupoCIC.repository.TipoItemRepository;
import es.santander.ascender.retoGrupoCIC.repository.FormatoRepository;
import es.santander.ascender.retoGrupoCIC.repository.TipoItemFormatoRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.Validator;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private TipoItemFormatoService tipoItemFormatoService;

    @Autowired
    private FormatoService formatoService;

    @Autowired
    private TipoItemService tipoItemService;

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private LibroService libroService;

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private MusicaService musicaService;

    @Autowired
    private Validator validator;

    public Item createItem(ItemDTO itemDTO) {
        // Obtener TipoItem y Formato por sus IDs
        Optional<TipoItem> tipoItemOptional = tipoItemService.obtenerTipoItemPorId(itemDTO.getTipoId());
        Optional<Formato> formatoOptional = formatoService.obtenerFormatoPorId(itemDTO.getFormatoId());

        if (!tipoItemOptional.isPresent()) {
            throw new TipoItemNotFoundException(itemDTO.getTipoId());
        }
        if (!formatoOptional.isPresent()) {
            throw new FormatoNotFoundException(itemDTO.getFormatoId());
        }

        TipoItem tipoItem = tipoItemOptional.get();
        Formato formato = formatoOptional.get();

        // Validar que el tipo cuadra con el formato introducido
        validateTipoItemFormato(tipoItem, formato);

        // Crear el Item
        Item item = new Item();
        item.setNombre(itemDTO.getNombre());
        item.setTipo(tipoItem);
        item.setFormato(formato);
        item.setUbicacion(itemDTO.getUbicacion());
        item.setFecha(itemDTO.getFecha());
        item.setEstado(itemDTO.getEstado() != null ? itemDTO.getEstado() : EstadoItem.DISPONIBLE);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        if (!violations.isEmpty()) {
            throw new CustomValidationException(violations);
        }

        Item savedItem = itemRepository.save(item);

        // Crear entidad específica según el tipo
        String tipoNombre = tipoItem.getNombre().toLowerCase();
        switch (tipoNombre) {
            case "libro":
                Libro libro = new Libro();
                libro.setItem(savedItem);
                libro.setAutor(itemDTO.getAutor());
                libro.setIsbn(itemDTO.getIsbn());
                libro.setEditorial(itemDTO.getEditorial());
                libro.setNumeroPaginas(itemDTO.getNumeroPaginas());
                libro.setFechaPublicacion(itemDTO.getFechaPublicacion());
                libroService.createLibro(libro);
                break;
            case "pelicula":
                Pelicula pelicula = new Pelicula();
                pelicula.setItem(savedItem);
                pelicula.setDirector(itemDTO.getDirector());
                pelicula.setDuracion(itemDTO.getDuracionPelicula());
                pelicula.setGenero(itemDTO.getGeneroPelicula());
                pelicula.setFechaEstreno(itemDTO.getFechaEstreno());
                peliculaService.createPelicula(pelicula);
                break;
            case "musica":
                Musica musica = new Musica();
                musica.setItem(savedItem);
                musica.setGenero(itemDTO.getGeneroMusica());
                musica.setCantante(itemDTO.getCantante());
                musica.setAlbum(itemDTO.getAlbum());
                musica.setDuracion(itemDTO.getDuracionMusica());
                musicaService.createMusica(musica);
                break;
        }

        return savedItem;
    }

    @Transactional(readOnly = true)
    public List<Item> obtenerItems() {
        return itemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Item> obtenerItemPorId(Long id) {
        return itemRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ItemCompletoDTO> obtenerItemsCompletos() {
        List<Item> items = itemRepository.findAll();
        List<ItemCompletoDTO> dtos = new ArrayList<>();

        for (Item item : items) {
            ItemCompletoDTO dto = new ItemCompletoDTO();

            // Mapear campos comunes
            dto.setId(item.getId());
            dto.setNombre(item.getNombre());
            dto.setUbicacion(item.getUbicacion());
            dto.setFecha(item.getFecha()); // Mantiene LocalDate
            dto.setEstado(item.getEstado());
            dto.setTipo(item.getTipo()); // Copia objeto completo
            dto.setFormato(item.getFormato()); // Copia objeto completo

            // Mapear campos específicos buscando en repositorios correspondientes
            if (item.getTipo() != null) {
                String tipoNombre = item.getTipo().getNombre().toLowerCase();
                switch (tipoNombre) {
                    case "libro":
                        libroRepository.findByItemId(item.getId()).ifPresent(libro -> {
                            dto.setAutor(libro.getAutor());
                            dto.setIsbn(libro.getIsbn());
                            dto.setEditorial(libro.getEditorial());
                            dto.setNumeroPaginas(libro.getNumeroPaginas());
                            dto.setFechaPublicacion(libro.getFechaPublicacion());
                        });
                        break;
                    case "pelicula":
                    case "película":
                        peliculaRepository.findByItemId(item.getId()).ifPresent(pelicula -> {
                            dto.setDirector(pelicula.getDirector());
                            dto.setDuracionPelicula(pelicula.getDuracion());
                            dto.setGeneroPelicula(pelicula.getGenero());
                            dto.setFechaEstreno(pelicula.getFechaEstreno());
                        });
                        break;
                    case "musica":
                    case "música":
                        musicaRepository.findByItemId(item.getId()).ifPresent(musica -> {
                            dto.setGeneroMusica(musica.getGenero());
                            dto.setCantante(musica.getCantante());
                            dto.setAlbum(musica.getAlbum());
                            dto.setDuracionMusica(musica.getDuracion());
                        });
                        break;
                }
            }
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public Optional<ItemCompletoDTO> obtenerItemCompletoPorId(Long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isEmpty()) {
            return Optional.empty();
        }

        Item item = itemOptional.get();
        ItemCompletoDTO dto = new ItemCompletoDTO();

        // Mapear campos comunes
        dto.setId(item.getId());
        dto.setNombre(item.getNombre());
        dto.setUbicacion(item.getUbicacion());
        dto.setFecha(item.getFecha());
        dto.setEstado(item.getEstado());
        dto.setTipo(item.getTipo());
        dto.setFormato(item.getFormato());

        // Mapear campos específicos
        if (item.getTipo() != null) {
            String tipoNombre = item.getTipo().getNombre().toLowerCase();
            switch (tipoNombre) {
                case "libro":
                    libroRepository.findByItemId(item.getId()).ifPresent(libro -> {
                        dto.setAutor(libro.getAutor());
                        dto.setIsbn(libro.getIsbn());
                        dto.setEditorial(libro.getEditorial());
                        dto.setNumeroPaginas(libro.getNumeroPaginas());
                        dto.setFechaPublicacion(libro.getFechaPublicacion());
                    });
                    break;
                case "pelicula":
                case "película":
                    peliculaRepository.findByItemId(item.getId()).ifPresent(pelicula -> {
                        dto.setDirector(pelicula.getDirector());
                        dto.setDuracionPelicula(pelicula.getDuracion());
                        dto.setGeneroPelicula(pelicula.getGenero());
                        dto.setFechaEstreno(pelicula.getFechaEstreno());
                    });
                    break;
                case "musica":
                case "música":
                    musicaRepository.findByItemId(item.getId()).ifPresent(musica -> {
                        dto.setGeneroMusica(musica.getGenero());
                        dto.setCantante(musica.getCantante());
                        dto.setAlbum(musica.getAlbum());
                        dto.setDuracionMusica(musica.getDuracion());
                    });
                    break;
            }
        }
        return Optional.of(dto);
    }

    public Item updateItem(Long id, ItemDTO itemDTO) {
        // Buscar el Item existente por ID
        Item itemExistente = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));

        // Obtener TipoItem y Formato desde el DTO usando repositorios
        TipoItem tipoItem = tipoItemService.obtenerTipoItemPorId(itemDTO.getTipoId())
                .orElseThrow(() -> new TipoItemNotFoundException(itemDTO.getTipoId()));
        Formato formato = formatoService.obtenerFormatoPorId(itemDTO.getFormatoId())
                .orElseThrow(() -> new FormatoNotFoundException(itemDTO.getFormatoId()));

        // Validar que el TipoItem y Formato son una combinación válida
        validateTipoItemFormato(tipoItem, formato);

        // Actualizar los campos del Item existente con datos del DTO
        itemExistente.setNombre(itemDTO.getNombre());
        itemExistente.setTipo(tipoItem);
        itemExistente.setFormato(formato);
        itemExistente.setUbicacion(itemDTO.getUbicacion());
        itemExistente.setFecha(itemDTO.getFecha());
        // Actualizar estado si viene en el DTO
        if (itemDTO.getEstado() != null) {
            itemExistente.setEstado(itemDTO.getEstado());
        }

        // Validar el Item base actualizado antes de guardar
        Set<ConstraintViolation<Item>> violations = validator.validate(itemExistente);
        if (!violations.isEmpty()) {
            throw new CustomValidationException(violations);
        }

        // Guardar el Item base actualizado (no es estrictamente necesario si las
        // relaciones no cambian,
        // pero asegura que los campos base se persistan)
        Item savedItem = itemRepository.save(itemExistente);

        // Actualizar la entidad específica (Libro, Pelicula, Musica)
        String tipoNombre = tipoItem.getNombre().toLowerCase();
        switch (tipoNombre) {
            case "libro":
                Libro libroExistente = libroRepository.findByItemId(savedItem.getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Error interno: Libro asociado no encontrado para item ID: " + savedItem.getId()));
                libroExistente.setAutor(itemDTO.getAutor());
                libroExistente.setIsbn(itemDTO.getIsbn());
                libroExistente.setEditorial(itemDTO.getEditorial());
                libroExistente.setNumeroPaginas(itemDTO.getNumeroPaginas());
                libroExistente.setFechaPublicacion(itemDTO.getFechaPublicacion());
                // Validar libroExistente si es necesario
                libroRepository.save(libroExistente); // Guardar cambios específicos
                break;
            case "pelicula":
            case "película":
                Pelicula peliculaExistente = peliculaRepository.findByItemId(savedItem.getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Error interno: Pelicula asociada no encontrada para item ID: " + savedItem.getId()));
                peliculaExistente.setDirector(itemDTO.getDirector());
                peliculaExistente.setDuracion(itemDTO.getDuracionPelicula());
                peliculaExistente.setGenero(itemDTO.getGeneroPelicula());
                peliculaExistente.setFechaEstreno(itemDTO.getFechaEstreno());
                // Validar peliculaExistente si es necesario
                peliculaRepository.save(peliculaExistente); // Guardar cambios específicos
                break;
            case "musica":
            case "música":
                Musica musicaExistente = musicaRepository.findByItemId(savedItem.getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Error interno: Musica asociada no encontrada para item ID: " + savedItem.getId()));
                musicaExistente.setGenero(itemDTO.getGeneroMusica());
                musicaExistente.setCantante(itemDTO.getCantante());
                musicaExistente.setAlbum(itemDTO.getAlbum());
                musicaExistente.setDuracion(itemDTO.getDuracionMusica());
                // Validar musicaExistente si es necesario
                musicaRepository.save(musicaExistente); // Guardar cambios específicos
                break;
            default:
                System.err.println("Advertencia: Tipo de item '" + tipoNombre
                        + "' no tiene una entidad específica asociada para actualizar.");
                break;
        }
        return savedItem;
    }

    public void deleteItem(Long id) {
        Optional<Item> itemOpcional = itemRepository.findById(id);

        if (itemOpcional.isEmpty()) {
            throw new ItemNotFoundException(id);
        }

        Item item = itemOpcional.get();

        // Verifica si tiene préstamos activos
        if (item.getEstado() == EstadoItem.PRESTADO ||
                prestamoRepository.findByItem(item).isPresent()) {
            throw new ItemAsociadoAPrestamoException(item.getNombre());
        }

        // Eliminar entidades asociadas
        musicaRepository.findByItemId(id).ifPresent(musicaRepository::delete);
        libroRepository.findByItemId(id).ifPresent(libroRepository::delete);
        peliculaRepository.findByItemId(id).ifPresent(peliculaRepository::delete);

        // Finalmente, eliminar el ítem
        itemRepository.deleteById(id);
    }

    // método que comprueba que la relación entre tipo y formato sea correcta
    private void validateTipoItemFormato(TipoItem tipoItem, Formato formato) {
        if (tipoItem == null || formato == null) {
            throw new IllegalArgumentException("El tipo del Item y el formato son obligatorios");
        }
        // Comprobar que el tipoItem existe
        Optional<TipoItem> tipoItemOptional = tipoItemService.obtenerTipoItemPorId(tipoItem.getId());
        if (!tipoItemOptional.isPresent()) {
            throw new TipoItemNotFoundException(tipoItem.getId());
        }
        // comprobar que el formato exista
        Optional<Formato> formatoOptional = formatoService.obtenerFormatoPorId(formato.getId());
        if (!formatoOptional.isPresent()) {
            throw new FormatoNotFoundException(formato.getId());
        }

        String itemNombre = tipoItemOptional.get().getNombre();
        String formatoNombre = formatoOptional.get().getNombre();

        Set<TipoItemFormato> tipoItemFormatos = tipoItemFormatoService.obtenerTipoItemFormatosPorTipoItem(tipoItem);
        boolean isValid = tipoItemFormatos.stream()
                .anyMatch(tif -> tif.getFormato().equals(formato));

        if (!isValid) {
            throw new FormatoNoValidoException(formatoNombre, itemNombre);
        }

    }

    public List<Item> searchItems(String nombre, String tipo, EstadoItem estado, String ubicacion) {
        if (!isValidSearchParameters(nombre, tipo, estado, ubicacion)) {
            return itemRepository.findAll();
        }

        if (nombre != null && !nombre.trim().isEmpty()) {
            if (tipo != null) {
                if (estado != null) {
                    if (ubicacion != null && !ubicacion.trim().isEmpty()) {
                        return itemRepository.findByNombreContainingIgnoreCaseAndTipo_NombreAndEstadoAndUbicacion(
                                nombre, tipo, estado, ubicacion);
                    } else {
                        return itemRepository.findByNombreContainingIgnoreCaseAndTipo_NombreAndEstado(nombre,
                                tipo, estado);
                    }
                } else if (ubicacion != null && !ubicacion.trim().isEmpty()) {
                    return itemRepository.findByNombreContainingIgnoreCaseAndTipo_NombreAndUbicacion(nombre,
                            tipo, ubicacion);
                } else {
                    return itemRepository.findByNombreContainingIgnoreCaseAndTipo_Nombre(nombre, tipo);
                }
            } else if (estado != null) {
                if (ubicacion != null && !ubicacion.trim().isEmpty()) {
                    return itemRepository.findByNombreContainingIgnoreCaseAndEstadoAndUbicacion(nombre, estado,
                            ubicacion);
                } else {
                    return itemRepository.findByNombreContainingIgnoreCaseAndEstado(nombre, estado);
                }
            } else if (ubicacion != null && !ubicacion.trim().isEmpty()) {
                return itemRepository.findByNombreContainingIgnoreCaseAndUbicacion(nombre, ubicacion);
            } else {
                return itemRepository.findByNombreContainingIgnoreCase(nombre);
            }
        } else if (tipo != null) {
            if (estado != null) {
                if (ubicacion != null && !ubicacion.trim().isEmpty()) {
                    return itemRepository.findByTipo_NombreAndEstadoAndUbicacion(tipo, estado, ubicacion);
                } else {
                    return itemRepository.findByTipo_NombreAndEstado(tipo, estado);
                }
            } else if (ubicacion != null && !ubicacion.trim().isEmpty()) {
                return itemRepository.findByTipo_NombreAndUbicacion(tipo, ubicacion);
            } else {
                return itemRepository.findByTipo_Nombre(tipo);
            }
        } else if (estado != null) {
            if (ubicacion != null && !ubicacion.trim().isEmpty()) {
                return itemRepository.findByEstadoAndUbicacion(estado, ubicacion);
            } else {
                return itemRepository.findByEstado(estado);
            }
        } else if (ubicacion != null && !ubicacion.trim().isEmpty()) {
            return itemRepository.findByUbicacion(ubicacion);
        } else {
            return itemRepository.findAll();
        }
    }

    private boolean isValidSearchParameters(String nombre, String tipo, EstadoItem estado, String ubicacion) {
        return nombre != null || tipo != null || estado != null || ubicacion != null;
    }

    public List<Item> getAvailableItems() {
        return itemRepository.findByEstado(EstadoItem.DISPONIBLE);
    }

    public List<Item> getBorrowedItems() {
        return itemRepository.findByEstado(EstadoItem.PRESTADO);
    }

    public Item updateItemEstado(Long id, EstadoItem nuevoEstado) {
        Item itemExistente = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));

        // Validar si el nuevo estado es nulo (opcional, pero recomendable)
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El nuevo estado no puede ser nulo.");
        }

        itemExistente.setEstado(nuevoEstado);
        // No es necesario validar toda la entidad Item aquí si solo cambia el estado,
        // a menos que tengas reglas específicas sobre transiciones de estado.
        return itemRepository.save(itemExistente);
    }

}
