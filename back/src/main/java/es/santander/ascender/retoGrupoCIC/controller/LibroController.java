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
        Item itemRecibido = libro.getItem();

        if (itemRecibido == null) {
            return new ResponseEntity<>("Error: No item asociado a la libro", HttpStatus.BAD_REQUEST);
        }

        itemRecibido.setTipo(tipoLibro);;

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
             return ResponseEntity.ok(""); // 200 OK con un cuerpo vacío
        } catch (IllegalArgumentException e) {
 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        }     

}
