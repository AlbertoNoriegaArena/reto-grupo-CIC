package es.santander.ascender.retoGrupoCIC.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Lazy;

import es.santander.ascender.retoGrupoCIC.config.CustomValidationException;
import es.santander.ascender.retoGrupoCIC.config.ItemNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.ItemAsociadoAPrestamoException;
import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.model.Libro;
import es.santander.ascender.retoGrupoCIC.repository.ItemRepository;
import es.santander.ascender.retoGrupoCIC.repository.LibroRepository;

@Service
@Transactional
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Lazy
    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private Validator validator;

    public Libro createLibro(Libro libro) {
        validarLibro(libro);
        return libroRepository.save(libro);
    }

    @Transactional(readOnly = true)
    public Libro retriveLibro(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Libro> retriveAllLibros() {
        return libroRepository.findAll();
    }

    public Libro updateLibro(Long id, Libro libro) {
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (libroOptional.isEmpty()) {
            throw new RuntimeException("No se encuentra el libro con ID: " + id);
        }
    
        Libro existingLibro = libroOptional.get();
    
        Optional<Item> itemOptional = itemRepository.findById(libro.getItem().getId());
        if (itemOptional.isEmpty()) {
            throw new ItemNotFoundException(libro.getItem().getId());
        }
    
        Item item = itemOptional.get();
    
        // Validar los cambios antes de guardarlos
        validarLibro(libro);
    
        // Actualizar el Item
        item.setNombre(libro.getItem().getNombre());
        item.setTipo(libro.getItem().getTipo());
        item.setFormato(libro.getItem().getFormato());
        item.setUbicacion(libro.getItem().getUbicacion());
        item.setFecha(libro.getItem().getFecha());
        item.setEstado(libro.getItem().getEstado());
        itemRepository.save(item);
    
        // Actualizar el Libro
        existingLibro.setAutor(libro.getAutor());
        existingLibro.setIsbn(libro.getIsbn());
        existingLibro.setEditorial(libro.getEditorial());
        existingLibro.setNumeroPaginas(libro.getNumeroPaginas());
        existingLibro.setFechaPublicacion(libro.getFechaPublicacion());
        existingLibro.setItem(item);
    
        return libroRepository.save(existingLibro);
    }


    public void deleteLibro(Long itemId) {
        // Buscar el libro asociado al itemId
        Libro libro = libroRepository.findByItemId(itemId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el libro con el itemId proporcionado"));

        // Verificar si el item tiene préstamos activos
        if (prestamoService.tienePrestamosAsociados(itemId)) {
            throw new ItemAsociadoAPrestamoException(libro.getItem().getNombre());
        }

        // Eliminar el libro
        libroRepository.delete(libro);

        // Eliminar el item asociado al libro
        Item item = libro.getItem();
        itemRepository.delete(item);
    }

    private void validarLibro(Libro libro) {
        Set<ConstraintViolation<Libro>> violations = validator.validate(libro);
        if (!violations.isEmpty()) {
            throw new CustomValidationException(violations);
        }
    }
    
}

