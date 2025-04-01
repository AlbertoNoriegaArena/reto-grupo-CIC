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
import es.santander.ascender.retoGrupoCIC.model.Musica;
import es.santander.ascender.retoGrupoCIC.repository.ItemRepository;
import es.santander.ascender.retoGrupoCIC.repository.MusicaRepository;

@Service
@Transactional
public class MusicaService {

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private Validator validator;

    public Musica createMusica(Musica musica) {
        validarMusica(musica);
        return musicaRepository.save(musica);
    }

    @Transactional(readOnly = true)
    public Musica retriveMusica(Long id) {
        return musicaRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Musica> retriveAllMusica() {
        return musicaRepository.findAll();
    }

    public Musica updateMusica(Long id, Musica musica) {
        Optional<Musica> musicaOptional = musicaRepository.findById(id);
        if (musicaOptional.isEmpty()) {
            throw new RuntimeException("No se encuentra la música con ID: " + id);
        }

        Musica existingMusica = musicaOptional.get();

        Optional<Item> itemOptional = itemRepository.findById(musica.getItem().getId());
        if (itemOptional.isEmpty()) {
            throw new ItemNotFoundException(musica.getItem().getId());
        }

        Item item = itemOptional.get();

        // Validar los cambios antes de guardarlos
        validarMusica(musica);

        // Actualizar el Item
        item.setNombre(musica.getItem().getNombre());
        item.setTipo(musica.getItem().getTipo());
        item.setFormato(musica.getItem().getFormato());
        item.setUbicacion(musica.getItem().getUbicacion());
        item.setFecha(musica.getItem().getFecha());
        item.setEstado(musica.getItem().getEstado());
        itemRepository.save(item);

        // Actualizar la Música
        existingMusica.setCantante(musica.getCantante());
        existingMusica.setDuracion(musica.getDuracion());
        existingMusica.setGenero(musica.getGenero());
        existingMusica.setAlbum(musica.getAlbum());
        existingMusica.setItem(item);

        return musicaRepository.save(existingMusica);
    }

    public void deleteMusica(Long itemId) {
        // Buscar la muscia asociada al itemId
        Musica musica = musicaRepository.findByItemId(itemId)
                .orElseThrow(
                        () -> new IllegalArgumentException("No se encontró la música con el itemId proporcionado"));

        // Verificar si el item tiene préstamos activos
        if (prestamoService.tienePrestamosAsociados(itemId)) {
            throw new ItemAsociadoAPrestamoException(musica.getItem().getNombre());
        }

        // Eliminar la musica
        musicaRepository.delete(musica);

        // Eliminar el item asociado a musica
        Item item = musica.getItem();
        itemRepository.delete(item);
    }

    private void validarMusica(Musica musica) {
        Set<ConstraintViolation<Musica>> violations = validator.validate(musica);
        if (!violations.isEmpty()) {
            throw new CustomValidationException(violations);
        }
    }
}
