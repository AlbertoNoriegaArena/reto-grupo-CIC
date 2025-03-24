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
import es.santander.ascender.retoGrupoCIC.model.Libro;
import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import es.santander.ascender.retoGrupoCIC.service.FormatoService;
import es.santander.ascender.retoGrupoCIC.service.LibroService;
import es.santander.ascender.retoGrupoCIC.service.TipoItemService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private FormatoService formatoService;

    @Autowired
    private TipoItemService tipoItemService;

   @PostMapping
    public ResponseEntity<?> create( @Valid @RequestBody Libro libro) {
        // Buscar el tipo "Libro"
        Optional<TipoItem> tipoLibroOpt = tipoItemService.obtenerPorNombre("Libro");

        if (tipoLibroOpt.isEmpty()) {
            return new ResponseEntity<>("Error: Tipo Libro no encontrado", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        TipoItem tipoLibro = tipoLibroOpt.get();

        // Obtener el tipo del item asociado a la libro
        TipoItem tipoItemRecibido = libro.getItem().getTipo();

        // Verificar si el tipoItem es null
        if (tipoItemRecibido == null || tipoItemRecibido.getId() == null) {
            return new ResponseEntity<>("Error: No se ha proporcionado un tipo de ítem válido", HttpStatus.BAD_REQUEST);
        }

        // Coger el tipoItem en la DDBB
        Optional<TipoItem> tipoItemBD = tipoItemService.obtenerTipoItemPorId(tipoItemRecibido.getId());

        if (tipoItemBD.isEmpty()) {
            throw new TipoItemNotFoundException(tipoItemRecibido.getId());
        }

        // Validar el tipo
        if (!tipoItemBD.get().getId().equals(tipoLibro.getId())) {
            return new ResponseEntity<>(
                    "Error: Solo se pueden crear libros. Has seleccionado: " + tipoItemBD.get().getNombre(),
                    HttpStatus.BAD_REQUEST);
        }

        // Validar formato
        List<Formato> formatosValidos = formatoService.obtenerFormatosPorTipoItem(tipoLibro);

        if (!formatosValidos.contains(libro.getItem().getFormato())) {
            return new ResponseEntity<>("Formato inválido para libros. Formatos válidos: "
                    + formatosValidos.stream().map(Formato::getNombre).toList(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(libroService.createLibro(libro), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Libro retrive(@PathVariable("id") Long id) {
        return libroService.retriveLibro(id);
    }

    @GetMapping
    public List<Libro> libro() {
        return libroService.retriveAllLibros();
    }

    // Método para actualizar la musica
    @PutMapping("/{itemId}")
    public ResponseEntity<Libro> update(@PathVariable("itemId") Long itemId, @Valid @RequestBody Libro libro) {
    
        Libro updatedLibro = libroService.updateLibro(itemId, libro);

        return new ResponseEntity<>(updatedLibro, HttpStatus.OK);
    }

     @DeleteMapping("/{itemId}")
     public ResponseEntity<String> delete(@PathVariable("itemId") Long itemId) {
        try {
             libroService.deleteLibro(itemId);
             return new ResponseEntity<>("Libro eliminada", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
 
             return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        }     

}
