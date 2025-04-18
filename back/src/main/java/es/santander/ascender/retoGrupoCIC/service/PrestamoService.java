package es.santander.ascender.retoGrupoCIC.service;

import es.santander.ascender.retoGrupoCIC.config.FechaDevolucionInvalidaException;
import es.santander.ascender.retoGrupoCIC.config.ItemNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.ItemObligatorioException;
import es.santander.ascender.retoGrupoCIC.config.ItemPrestadoException;
import es.santander.ascender.retoGrupoCIC.config.PersonaNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.PersonaObligatoriaException;
import es.santander.ascender.retoGrupoCIC.config.PrestamoBorradoException;
import es.santander.ascender.retoGrupoCIC.config.PrestamoNotFoundException;
import es.santander.ascender.retoGrupoCIC.model.EstadoItem;
import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.model.Persona;
import es.santander.ascender.retoGrupoCIC.model.Prestamo;
import es.santander.ascender.retoGrupoCIC.repository.ItemRepository;
import es.santander.ascender.retoGrupoCIC.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest; 
import org.springframework.data.domain.Pageable;   
import org.springframework.data.domain.Sort;      

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private ItemRepository itemRepository;

    public Prestamo createPrestamo(Prestamo prestamo) {

        comprobacionesItemYPersona(prestamo);

        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(null);

        validacionFechaPrevistaDevolucion(prestamo);

    
       Item item = itemRepository.findById(prestamo.getItemId()) 
       .orElseThrow(() -> new ItemNotFoundException(prestamo.getItemId()));
        
        itemService.updateItemEstado(item.getId(), EstadoItem.PRESTADO);


        prestamo.setItem(item);
        prestamo.setPersona(personaService.getPersonaById(prestamo.getPersona().getId()));
        return prestamoRepository.save(prestamo);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> getAllPrestamos() {
        // Devuelve solo préstamos NO borrados
        return prestamoRepository.findByBorradoFalse();
    }

    @Transactional(readOnly = true)
    public Optional<Prestamo> getPrestamoById(Long id) {
        Optional<Prestamo> prestamo = prestamoRepository.findById(id);
        // Si el préstamo está borrado, se devuelve vacío
        return prestamo.filter(p -> !p.isBorrado());
    }

    public Prestamo updatePrestamo(Long id, Prestamo prestamo) {
        // Buscar el préstamo por ID
        Optional<Prestamo> prestamoOptional = prestamoRepository.findById(id);
        if (prestamoOptional.isEmpty()) {
            throw new PrestamoNotFoundException(id);
        }

        Prestamo prestamoExistente = prestamoOptional.get();

        // Validar Item y Persona
        comprobacionesItemYPersona(prestamo);

        // Validar Fecha Prevista de Devolución
        validacionFechaPrevistaDevolucion(prestamo);

        // Actualizar datos del préstamo
        prestamoExistente.setFechaPrevistaDevolucion(prestamo.getFechaPrevistaDevolucion());

        // Si el préstamo se ha devuelto, actualizar la fecha de devolución y el estado
        // del Item
        if (prestamo.getFechaDevolucion() != null) {
            prestamoExistente.setFechaDevolucion(prestamo.getFechaDevolucion());

            // Actualizar estado del Item a DISPONIBLE
            Optional<Item> itemOptional = itemService.obtenerItemPorId(prestamoExistente.getItemId());
            if (itemOptional.isEmpty()) {
                throw new ItemNotFoundException(prestamoExistente.getItemId());
            }

            Item itemDevuelto = itemOptional.get();
            itemDevuelto.setEstado(EstadoItem.DISPONIBLE);
            itemService.updateItemEstado(itemDevuelto.getId(), EstadoItem.DISPONIBLE);
        }

        // Guardar los cambios
        return prestamoRepository.save(prestamoExistente);
    }

    public void deletePrestamo(Long id) {
        Optional<Prestamo> prestamoOptional = prestamoRepository.findById(id);
        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();

            if (prestamo.isBorrado()) {
                throw new PrestamoBorradoException(id);
            }

            if (prestamo.getFechaDevolucion() == null) {
                throw new ItemPrestadoException(prestamo.getItem().getNombre());
            }

            // Se marca como borrado, no se elimina el registro
            prestamo.setBorrado(true);

            prestamoRepository.save(prestamo);
        } else {
            throw new PrestamoNotFoundException(id);
        }
    }

    // metodo para devolver un prestamo
    public Prestamo devolverPrestamo(Long id) {
        Optional<Prestamo> prestamoOptional = prestamoRepository.findById(id);
        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();
            if (prestamo.getFechaDevolucion() != null) {
                throw new IllegalArgumentException("El item ya ha sido devuelto");
            }
            prestamo.setFechaDevolucion(LocalDate.now());
            Item item = prestamo.getItem(); 
            if (item == null) { 
                 item = itemRepository.findById(prestamo.getItemId())
                            .orElseThrow(() -> new ItemNotFoundException(prestamo.getItemId()));
            }
            itemService.updateItemEstado(item.getId(), EstadoItem.DISPONIBLE); 
    
            return prestamoRepository.save(prestamo);
        }
        throw new PrestamoNotFoundException(id);
    }

    public List<Prestamo> getPrestamosByPersonaId(Long personaId) {
        Persona persona = personaService.getPersonaById(personaId);
        if (persona == null) {
            throw new PersonaNotFoundException(personaId);
        }
        return prestamoRepository.findByPersonaAndBorradoFalse(persona);
    }

    private void comprobacionesItemYPersona(Prestamo prestamo) {

        if (prestamo.getItemId() == null) {
            throw new ItemObligatorioException();
        }
        if (prestamo.getPersona() == null) {
            throw new PersonaObligatoriaException();
        }
        Optional<Item> itemOptional = itemService.obtenerItemPorId(prestamo.getItemId());
        Persona persona = personaService.getPersonaById(prestamo.getPersona().getId());

        // Comprobar que exista Item
        if (!itemOptional.isPresent()) {
            throw new ItemNotFoundException(prestamo.getItemId());
        }
        // Comprobar que exista persona
        if (persona == null) {
            throw new PersonaNotFoundException(prestamo.getPersona().getId());
        }

        // Si el ítem está prestado, pero estamos solo actualizando la fecha prevista de devolución, no lanzamos la excepción.
        Item item = itemOptional.get();
        if (item.getEstado() == EstadoItem.PRESTADO && prestamo.getFechaPrevistaDevolucion() == null) {
            throw new ItemPrestadoException(item.getNombre());
        }
    }

    private void validacionFechaPrevistaDevolucion(Prestamo prestamo) {
        // Validar que la fecha prevista de devolución sea posterior a la fecha de
        // préstamo
        if (prestamo.getFechaPrevistaDevolucion() != null
                && prestamo.getFechaPrevistaDevolucion().isBefore(prestamo.getFechaPrestamo())) {
            throw new FechaDevolucionInvalidaException(
                    "La fecha prevista de devolución no puede ser antes de la fecha de préstamo");
        }
    }

    public boolean tienePrestamosAsociados(Long itemId) {
        // Buscar el Item por su ID
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el item con el id: " + itemId));

        // Verificar si existen préstamos asociados a ese item
        return prestamoRepository.existsByItem(item);
    }

    // Listado de libros que deberían haberse devuelto y no se ha hecho
    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamosVencidos() {
        LocalDate fechaHoy = LocalDate.now();
        return prestamoRepository.findByFechaPrevistaDevolucionBeforeAndFechaDevolucionIsNull(fechaHoy);
    }

    public List<Prestamo> listarPrestamosActivos(String nombrePersona, LocalDate fechaPrevistaDevolucion) {
        List<Prestamo> prestamos = prestamoRepository.findByFechaDevolucionIsNull();

        if (nombrePersona != null && !nombrePersona.isEmpty()) {
            prestamos = prestamos.stream()
                    .filter(p -> p.getPersona().getNombre().equalsIgnoreCase(nombrePersona))
                    .toList();
        }

        if (fechaPrevistaDevolucion != null) {
            prestamos = prestamos.stream()
                    .filter(p -> p.getFechaPrevistaDevolucion() != null &&
                            p.getFechaPrevistaDevolucion().equals(fechaPrevistaDevolucion))
                    .toList();
        }

        return prestamos;
    }

    @Transactional(readOnly = true)
    public List<Prestamo> findUltimosPorItem(Long itemId, int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "fechaPrestamo"));
        return prestamoRepository.findRecentPrestamosByItem(itemId, pageable);
    }
    
}
