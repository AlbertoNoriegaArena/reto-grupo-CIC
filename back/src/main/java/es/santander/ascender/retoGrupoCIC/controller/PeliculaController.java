package es.santander.ascender.retoGrupoCIC.controller;

import java.util.List;

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

import es.santander.ascender.retoGrupoCIC.model.Pelicula;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormato;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormatoId;
import es.santander.ascender.retoGrupoCIC.service.PeliculaService;
import es.santander.ascender.retoGrupoCIC.service.TipoItemFormatoService;

@RestController
@RequestMapping("/api/pelicula")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private TipoItemFormatoService tipoItemFormatoService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Pelicula pelicula) {
        TipoItemFormatoId tipoItemFormatoId = new TipoItemFormatoId(pelicula.getItem().getFormato().getId(), pelicula.getItem().getTipo().getId());
        TipoItemFormato tipoItemFormat = tipoItemFormatoService.obtenerTipoItemFormatoPorId(tipoItemFormatoId).orElse(null);
        if (tipoItemFormat == null) {
            return new ResponseEntity<>("Tipo y formato ilegal.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(peliculaService.createPelicula(pelicula), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Pelicula retrive(@PathVariable("id") Long id) {
        return peliculaService.retrivePelicula(id);
    }

    @PutMapping("/{id}")
    public Pelicula update(@PathVariable("id") Long id, @RequestBody Pelicula pelicula) {
        return peliculaService.updatePelicula(id, pelicula);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        peliculaService.deletePelicula(id);
        return "delete";
    }

    @GetMapping
    public List<Pelicula> pelicula() {
        return peliculaService.retriveAllPeliculas();
    }


}
