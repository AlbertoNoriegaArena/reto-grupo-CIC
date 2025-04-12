package es.santander.ascender.retoGrupoCIC.service;

import es.santander.ascender.retoGrupoCIC.config.CustomValidationException;
import es.santander.ascender.retoGrupoCIC.config.FormatoNoValidoException;
import es.santander.ascender.retoGrupoCIC.config.FormatoNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.ItemAsociadoAPrestamoException;
import es.santander.ascender.retoGrupoCIC.config.ItemNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.ItemPrestadoException;
import es.santander.ascender.retoGrupoCIC.config.TipoItemNotFoundException;
import es.santander.ascender.retoGrupoCIC.dto.ItemDTO;
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
import jakarta.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
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

    public Item updateItem(Long id, Item itemActualizado) {
        Optional<Item> itemOpcional = itemRepository.findById(id);
        if (itemOpcional.isPresent()) {
            Item itemExistente = itemOpcional.get();

            // Primero validamos que el tipo y el formato cumplan
            validateTipoItemFormato(itemActualizado.getTipo(), itemActualizado.getFormato());

            // Actualizar los campos menos el estado
            itemExistente.setNombre(itemActualizado.getNombre());
            itemExistente.setTipo(itemActualizado.getTipo());
            itemExistente.setFormato(itemActualizado.getFormato());
            itemExistente.setUbicacion(itemActualizado.getUbicacion());
            itemExistente.setFecha(itemActualizado.getFecha());

            return itemRepository.save(itemExistente);
        }
        return null;
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
}
