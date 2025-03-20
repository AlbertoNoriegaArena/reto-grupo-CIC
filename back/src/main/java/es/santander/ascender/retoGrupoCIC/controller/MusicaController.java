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

import es.santander.ascender.retoGrupoCIC.model.Musica;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormato;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormatoId;
import es.santander.ascender.retoGrupoCIC.service.MusicaService;
import es.santander.ascender.retoGrupoCIC.service.TipoItemFormatoService;

@RestController
@RequestMapping("/api/musica")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

    @Autowired
    private TipoItemFormatoService tipoItemFormatoService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Musica musica) {
        TipoItemFormatoId tipoItemFormatoId = new TipoItemFormatoId(musica.getItem().getFormato().getId(), musica.getItem().getTipo().getId());
        TipoItemFormato tipoItemFormat = tipoItemFormatoService.obtenerTipoItemFormatoPorId(tipoItemFormatoId).orElse(null);
        if (tipoItemFormat == null) {
            return new ResponseEntity<>("Tipo y formato ilegal.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(musicaService.createMusica(musica), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public Musica retrive(@PathVariable("id") Long id) {
        return musicaService.retriveMusica(id);
    }

    @PutMapping("/{id}")
    public Musica update(@PathVariable("id") Long id, @RequestBody Musica musica) {
        return musicaService.updateMusica(id, musica);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        musicaService.deleteMusica(id);
        return "delete";
    }

    @GetMapping
    public List<Musica> musica() {
        return musicaService.retriveAllMusica();
    }


}
