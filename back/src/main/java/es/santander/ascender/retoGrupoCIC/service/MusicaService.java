package es.santander.ascender.retoGrupoCIC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.santander.ascender.retoGrupoCIC.model.Libro;
import es.santander.ascender.retoGrupoCIC.model.Musica;
import es.santander.ascender.retoGrupoCIC.repository.MusicaRepository;

@Service
public class MusicaService {

    @Autowired
    private MusicaRepository musicaRepository;

    public Musica createMusica(Musica musica, Long itemId) {
        return musicaRepository.save(musica);
    }

    public void deleteMusica(Long id) {
        musicaRepository.deleteById(id);
    }

    public Musica retriveMusica(Long id) {
        return musicaRepository.findById(id).orElse(null);
    }

    public List<Musica> retriveAllMusica() {
        return musicaRepository.findAll();
    }

    public Musica updateMusica(Long id, Musica musica) {
        if (musicaRepository.existsById(id)) {
            musica.setItemId(id);
            return musicaRepository.save(musica);
        }
        throw new RuntimeException("No pude encontrar el musica con id" + id);
    }


}
