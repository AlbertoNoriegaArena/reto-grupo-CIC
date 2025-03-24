package es.santander.ascender.retoGrupoCIC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    
    public Pelicula createPelicula(Pelicula pelicula) {
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
        if (peliculaRepository.existsById(id)) {
            pelicula.setItemId(id);
            return peliculaRepository.save(pelicula);
        }
        throw new RuntimeException("No pude encontrar la pelicula con id" + id);
    }

    // Método para eliminar la película y el item asociado
    public void deletePelicula(Long itemId) {
        // Buscar la película asociada al itemId
        Pelicula pelicula = peliculaRepository.findByItemId(itemId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la película con el itemId proporcionado"));

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
}
