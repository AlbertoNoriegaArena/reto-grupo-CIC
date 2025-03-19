package es.santander.ascender.retoGrupoCIC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.santander.ascender.retoGrupoCIC.model.Libro;
import es.santander.ascender.retoGrupoCIC.service.LibroService;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    /*@PostMapping
    public Libro create(@RequestBody Libro libro, Long itemId) {
        return libroService.createLibro(libro);
    }*/

    @GetMapping("/{id}")
    public Libro retrive(@PathVariable("id") Long id) {
        return libroService.retriveLibro(id);
    }

    @PutMapping("/{id}")
    public Libro update(@PathVariable("id") Long id, @RequestBody Libro libro) {
        return libroService.updateLibro(id, libro);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        libroService.deleteLibro(id);
        return "delete";
    }

    @GetMapping
    public List<Libro> libro() {
        return libroService.retriveAllLibros();
    }

}
