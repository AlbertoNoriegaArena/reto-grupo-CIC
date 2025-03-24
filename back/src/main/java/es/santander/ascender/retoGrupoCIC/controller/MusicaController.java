package es.santander.ascender.retoGrupoCIC.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.santander.ascender.retoGrupoCIC.config.TipoItemNotFoundException;
import es.santander.ascender.retoGrupoCIC.model.Formato;
import es.santander.ascender.retoGrupoCIC.model.Musica;
import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import es.santander.ascender.retoGrupoCIC.service.FormatoService;
import es.santander.ascender.retoGrupoCIC.service.MusicaService;
import es.santander.ascender.retoGrupoCIC.service.TipoItemService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/musica")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

    @Autowired
    private FormatoService formatoService;

    @Autowired
    private TipoItemService tipoItemService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Musica musica) {
        // Buscar el tipo "Musica"
        Optional<TipoItem> tipoMusicaOpt = tipoItemService.obtenerPorNombre("Musica");

        if (tipoMusicaOpt.isEmpty()) {
            return new ResponseEntity<>("Error: Tipo Musica no encontrado", HttpStatus.INTERNAL_SERVER_ERROR);    
        }

        TipoItem tipoMusica = tipoMusicaOpt.get();

        // Obtener el tipo del item asociado a la musica
        TipoItem tipoItemRecibido = musica.getItem().getTipo();

        // Verificar si el tipoItem es null
        if (tipoItemRecibido == null || tipoItemRecibido.getId() == null) {
            return new ResponseEntity<>("Error: No se ha proporcionado un tipo de ítem válido", HttpStatus.BAD_REQUEST);
        }

        // Coger el tipoItem en la DDBB
        Optional<TipoItem> tipoItemBD = tipoItemService.obtenerTipoItemPorId(tipoItemRecibido.getId());

        if(tipoItemBD.isEmpty()) {
            throw new TipoItemNotFoundException(tipoItemRecibido.getId());
        }

        // Validar el tipo
        if (tipoItemBD.get().getId().equals(tipoMusica.getId())) {
            return new ResponseEntity<>(
                "Error: Solo se pueden crear musica. Has seleccionado: " + tipoItemBD.get().getNombre(),
                 HttpStatus.BAD_REQUEST);
        }

        // Validar formato
        List<Formato> formatosValidos = formatoService.obtenerFormatosPorTipoItem(tipoMusica);

        if (!formatosValidos.contains(musica.getItem().getFormato())) {
            return new ResponseEntity<>("Formato inválido para musica. Formatos válidos:"
                + formatosValidos.stream().map(Formato::getNombre).toList(), HttpStatus.BAD_REQUEST);   
        }
       
        return new ResponseEntity<>(musicaService.createMusica(musica), HttpStatus.CREATED);
    }    
    @GetMapping("/{id}")
    public Musica retrive(@PathVariable("id") Long id) {
        return musicaService.retriveMusica(id);
    }

    @GetMapping
    public List<Musica> musica() {
        return musicaService.retriveAllMusica();
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Musica> update(@PathVariable("itemId") Long itemId, @Valid @RequestBody Musica musica) {
        
        Musica updateMusica = musicaService.updateMusica(itemId, musica);
        
        return new ResponseEntity<>(updateMusica, HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> delete(@PathVariable("itemId") Long itemId) {
        try {
            musicaService.deleteMusica(itemId);
            return new ResponseEntity<>("Musica eliminada", HttpStatus.OK);
        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
