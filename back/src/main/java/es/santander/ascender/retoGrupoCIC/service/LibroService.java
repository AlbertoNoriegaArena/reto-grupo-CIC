package es.santander.ascender.retoGrupoCIC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.santander.ascender.retoGrupoCIC.config.ItemAsociadoAPrestamoException;
import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.model.Libro;
import es.santander.ascender.retoGrupoCIC.repository.ItemRepository;
import es.santander.ascender.retoGrupoCIC.repository.LibroRepository;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private ItemRepository itemRepository;

    public Libro createLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public Libro retriveLibro(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    public List<Libro> retriveAllLibros() {
        return libroRepository.findAll();
    }

    public Libro updateLibro(Long id, Libro libro) {
        if (libroRepository.existsById(id)) {
            libro.setItemId(id);
            return libroRepository.save(libro);
        }
        throw new RuntimeException("No pude encontrar el libro con id" + id);
    }


    public void deleteLibro(Long itemId) {
        // Buscar el libro asociado al itemId
        Libro libro = libroRepository.findByItemId(itemId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el libro con el itemId proporcionado"));

        // Verificar si el item tiene préstamos activos
        if (prestamoService.tienePrestamosAsociados(itemId)) {
            throw new ItemAsociadoAPrestamoException(libro.getItem().getNombre());
        }

        // Eliminar la película
        libroRepository.delete(libro);

        // Eliminar el item asociado a la película
        Item item = libro.getItem();
        itemRepository.delete(item);
    }
    
}

