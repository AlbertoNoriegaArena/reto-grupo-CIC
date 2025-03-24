package es.santander.ascender.retoGrupoCIC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.santander.ascender.retoGrupoCIC.config.ItemAsociadoAPrestamoException;
import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.model.Musica;
import es.santander.ascender.retoGrupoCIC.repository.ItemRepository;
import es.santander.ascender.retoGrupoCIC.repository.MusicaRepository;

@Service
public class MusicaService {

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private ItemRepository itemRepository;

    public Musica createMusica(Musica musica) {
        return musicaRepository.save(musica);
    }

    public Musica retriveMusica(Long id) {
        return musicaRepository.findById(id).orElse(null);
    }

    public List<Musica> retriveAllMusica() {
        return musicaRepository.findAll();
    }

    public Musica updateMusica(Long id, Musica pelicula) {
        if (musicaRepository.existsById(id)) {
            pelicula.setItemId(id);
            return musicaRepository.save(pelicula);
        }
        throw new RuntimeException("No pude encontrar la musica con id" + id);
    }

    public void deleteMusica(Long itemId) {
        // Buscar la muscia asociada al itemId
        Musica pelicula = musicaRepository.findByItemId(itemId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la película con el itemId proporcionado"));

        // Verificar si el item tiene préstamos activos
        if (prestamoService.tienePrestamosAsociados(itemId)) {
            throw new ItemAsociadoAPrestamoException(pelicula.getItem().getNombre());
        }

        // Eliminar la musica
        musicaRepository.delete(pelicula);

        // Eliminar el item asociado a musica
        Item item = pelicula.getItem();
        itemRepository.delete(item);
    }


}
