package es.santander.ascender.retoGrupoCIC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.santander.ascender.retoGrupoCIC.model.Pelicula;
import es.santander.ascender.retoGrupoCIC.repository.PeliculaRepository;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    public Pelicula createPelicula(Pelicula pelicula, Long itemId) {
        return peliculaRepository.save(pelicula);
    }

    public void deletePelicula(Long id) {
        peliculaRepository.deleteById(id);
    }

    public Pelicula retrivePelicula(Long id) {
        return peliculaRepository.findById(id).orElse(null);
    }

    public List<Pelicula> retriveAllLibros() {
        return peliculaRepository.findAll();
    }

    public Pelicula updatePelicula(Long id, Pelicula pelicula) {
        if (peliculaRepository.existsById(id)) {
            pelicula.setItemId(id);
            return peliculaRepository.save(pelicula);
        }
        throw new RuntimeException("No pude encontrar el pelicula con id" + id);
    }

}
