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
import es.santander.ascender.retoGrupoCIC.model.Musica;
import es.santander.ascender.retoGrupoCIC.service.MusicaService;

@RestController
@RequestMapping("/api/musica")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

    // @PostMapping
    // public Music create(@RequestBody Musica musica, Long itemId) {
    //     return musicaService.createMusica(musica);
    // }

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
