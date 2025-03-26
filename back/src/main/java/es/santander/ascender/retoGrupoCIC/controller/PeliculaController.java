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

import es.santander.ascender.retoGrupoCIC.model.Formato;
import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.model.Pelicula;
import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import es.santander.ascender.retoGrupoCIC.service.FormatoService;
import es.santander.ascender.retoGrupoCIC.service.PeliculaService;
import es.santander.ascender.retoGrupoCIC.service.TipoItemService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pelicula")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private FormatoService formatoService;

    @Autowired
    private TipoItemService tipoItemService;

    @PostMapping
    public ResponseEntity<?> create( @Valid @RequestBody Pelicula pelicula) {
        // Buscar el tipo "Película"
        Optional<TipoItem> tipoPeliculaOpt = tipoItemService.obtenerPorNombre("Pelicula");

        if (tipoPeliculaOpt.isEmpty()) {
            return new ResponseEntity<>("Error: Tipo Pelicula no encontrado", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        TipoItem tipoPelicula = tipoPeliculaOpt.get();

        // Obtener el tipo del item asociado a la película
        Item itemRecibido = pelicula.getItem();

        if (itemRecibido == null) {
            return new ResponseEntity<>("Error: No item asociado a la pelicula", HttpStatus.BAD_REQUEST);
        }

        itemRecibido.setTipo(tipoPelicula);;

        // Validar formato
        List<Formato> formatosValidos = formatoService.obtenerFormatosPorTipoItem(tipoPelicula);

        if (!formatosValidos.contains(pelicula.getItem().getFormato())) {
            return new ResponseEntity<>("Formato inválido para películas. Formatos válidos: "
                    + formatosValidos.stream().map(Formato::getNombre).toList(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(peliculaService.createPelicula(pelicula), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Pelicula retrive(@PathVariable("id") Long id) {
        return peliculaService.retrivePelicula(id);
    }

    @GetMapping
    public List<Pelicula> pelicula() {
        return peliculaService.retriveAllPeliculas();
    }

    // Método para actualizar la película
    @PutMapping("/{itemId}")
    public ResponseEntity<Pelicula> update(@PathVariable("itemId") Long itemId, @Valid @RequestBody Pelicula pelicula) {
    
        Pelicula updatedPelicula = peliculaService.updatePelicula(itemId, pelicula);

        return new ResponseEntity<>(updatedPelicula, HttpStatus.OK);
    }

    // Método para eliminar la película
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> delete(@PathVariable("itemId") Long itemId) {
        try {
            peliculaService.deletePelicula(itemId);
            return new ResponseEntity<>("Película eliminada", HttpStatus.OK);
        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
