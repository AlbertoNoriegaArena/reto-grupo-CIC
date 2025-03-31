package es.santander.ascender.retoGrupoCIC.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.santander.ascender.retoGrupoCIC.config.CustomValidationException;
import es.santander.ascender.retoGrupoCIC.config.ItemNotFoundException;
import es.santander.ascender.retoGrupoCIC.config.ItemAsociadoAPrestamoException;
import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.model.Pelicula;
import es.santander.ascender.retoGrupoCIC.repository.ItemRepository;
import es.santander.ascender.retoGrupoCIC.repository.PeliculaRepository;

@Service
@Transactional
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private Validator validator;

    public Pelicula createPelicula(Pelicula pelicula) {
        validarPelicula(pelicula);
        return peliculaRepository.save(pelicula);
    }

    @Transactional(readOnly = true)
    public Pelicula retrivePelicula(Long id) {
        return peliculaRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Pelicula> retriveAllPeliculas() {
        return peliculaRepository.findAll();
    }

    public Pelicula updatePelicula(Long id, Pelicula pelicula) {
        Optional<Pelicula> peliculaOptional = peliculaRepository.findById(id);
        if (peliculaOptional.isEmpty()) {
            throw new RuntimeException("No se encuentra la película con ID: " + id);
        }

        Pelicula existingPelicula = peliculaOptional.get();

        Optional<Item> itemOptional = itemRepository.findById(pelicula.getItem().getId());
        if (itemOptional.isEmpty()) {
            throw new ItemNotFoundException(pelicula.getItem().getId());
        }

        Item item = itemOptional.get();

        // Validar los cambios antes de guardarlos
        validarPelicula(pelicula);

        // Actualizar el Item
        item.setNombre(pelicula.getItem().getNombre());
        item.setTipo(pelicula.getItem().getTipo());
        item.setFormato(pelicula.getItem().getFormato());
        item.setUbicacion(pelicula.getItem().getUbicacion());
        item.setFecha(pelicula.getItem().getFecha());
        item.setEstado(pelicula.getItem().getEstado());
        itemRepository.save(item);

        // Actualizar la Película
        existingPelicula.setDirector(pelicula.getDirector());
        existingPelicula.setDuracion(pelicula.getDuracion());
        existingPelicula.setGenero(pelicula.getGenero());
        existingPelicula.setFechaEstreno(pelicula.getFechaEstreno());
        existingPelicula.setItem(item);

        return peliculaRepository.save(existingPelicula);
    }

    // Método para eliminar la película y el item asociado
    public void deletePelicula(Long itemId) {
        // Buscar la película asociada al itemId
        Pelicula pelicula = peliculaRepository.findByItemId(itemId)
                .orElseThrow(
                        () -> new IllegalArgumentException("No se encontró la película con el itemId proporcionado"));

        // Verificar si el item tiene préstamos activos
        if (prestamoService.tienePrestamosAsociados(itemId)) {
            throw new ItemAsociadoAPrestamoException(pelicula.getItem().getNombre());
        }

        // Eliminar la película
        peliculaRepository.delete(pelicula);

        // Eliminar el item asociado a la película
        Item item = pelicula.getItem();
        itemRepository.delete(item);
    }

    private void validarPelicula(Pelicula pelicula) {
        Set<ConstraintViolation<Pelicula>> violations = validator.validate(pelicula);
        if (!violations.isEmpty()) {
            throw new CustomValidationException(violations);
        }
    }
}
