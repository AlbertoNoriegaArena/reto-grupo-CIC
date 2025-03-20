package es.santander.ascender.retoGrupoCIC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.santander.ascender.retoGrupoCIC.model.Musica;
import es.santander.ascender.retoGrupoCIC.model.Pelicula;
import es.santander.ascender.retoGrupoCIC.service.PeliculaService;

@RestController
@RequestMapping("/api/pelicula")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    // @PostMapping
    // public Pelicula create(@RequestBody Pelicula pelicula, Long itemId) {
    //     return peliculaService.createPelicula(pelicula);
    // }

    @GetMapping("/{id}")
    public Pelicula retrive(@PathVariable("id") Long id) {
        return peliculaService.retrivePelicula(id);
    }

    // @PutMapping("/{id}")
    // public Pelicula update(@PathVariable("id") Long id, @RequestBody Musica musica) {
    //     return peliculaService.updatePelicula(id, musica);
    // }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        peliculaService.deletePelicula(id);
        return "delete";
    }

    // @GetMapping
    // public List<Pelicula> pelicula() {
    //     return peliculaService.retriveAllPelicula();
    // }


}
