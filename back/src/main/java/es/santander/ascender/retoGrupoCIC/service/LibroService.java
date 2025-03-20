package es.santander.ascender.retoGrupoCIC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.santander.ascender.retoGrupoCIC.model.Libro;
import es.santander.ascender.retoGrupoCIC.repository.LibroRepository;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public Libro createLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }

    public Libro retriveLibro(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    public List<Libro> retriveAllLibros() {
        return libroRepository.findAll();
    }

    public Libro updateLibro(Long id, Libro libro) {
        if (libroRepository.existsById(id)) {
            libro.setItemId(id);
            return libroRepository.save(libro);
        }
        throw new RuntimeException("No pude encontrar el libro con id" + id);
    }


    
}

