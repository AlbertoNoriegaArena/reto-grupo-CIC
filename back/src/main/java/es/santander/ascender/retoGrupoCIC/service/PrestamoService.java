package es.santander.ascender.retoGrupoCIC.service;

import es.santander.ascender.retoGrupoCIC.model.EstadoItem;
import es.santander.ascender.retoGrupoCIC.model.Item;
import es.santander.ascender.retoGrupoCIC.model.Persona;
import es.santander.ascender.retoGrupoCIC.model.Prestamo;
import es.santander.ascender.retoGrupoCIC.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PersonaService personaService;

    public Prestamo createPrestamo(Prestamo prestamo) {
        
        if (prestamo.getItem() == null || prestamo.getPersona() == null) {
            throw new IllegalArgumentException("El item y la persona son obligatorios");
        }
        // Comprobar que exista Item
        Optional<Item> itemOptional = itemService.obtenerItemPorId(prestamo.getItem().getId());
        if (!itemOptional.isPresent()) {
            throw new IllegalArgumentException("El item no existe");
        }
        // Comprobar que exista persona
        Persona persona = personaService.getPersonaById(prestamo.getPersona().getId());
        if (persona == null) {
            throw new IllegalArgumentException("La persona no existe");
        }
        // Comprobar que el item esté dispnible
        Item item = itemOptional.get();
        if (item.getEstado() == EstadoItem.PRESTADO) {
            throw new IllegalArgumentException("El item no está disponible");
        }

        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(null);
        
        item.setEstado(EstadoItem.PRESTADO);
        itemService.updateItem(item.getId(), item);

        return prestamoRepository.save(prestamo);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> getAllPrestamos() {
        return prestamoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Prestamo> getPrestamoById(Long id) {
        return prestamoRepository.findById(id);
    }

    public Prestamo updatePrestamo(Long id, Prestamo prestamo) {
        Optional<Prestamo> prestamoOptional = prestamoRepository.findById(id);
        if (prestamoOptional.isPresent()) {
            Prestamo prestamoExistente = prestamoOptional.get();
            
            if (prestamo.getFechaPrevistaDevolucion() != null) {
                prestamoExistente.setFechaPrevistaDevolucion(prestamo.getFechaPrevistaDevolucion());
            }
            if (prestamo.getFechaDevolucion() != null) {
                prestamoExistente.setFechaDevolucion(prestamo.getFechaDevolucion());
                
                Item item = prestamoExistente.getItem();
                item.setEstado(EstadoItem.DISPONIBLE);
                itemService.updateItem(item.getId(), item);
            }

            return prestamoRepository.save(prestamoExistente);
        }
        return null;
    }

    public void deletePrestamo(Long id) {
        Optional<Prestamo> prestamoOptional = prestamoRepository.findById(id);
        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();
            
            Item item = prestamo.getItem();
            item.setEstado(EstadoItem.DISPONIBLE);
            itemService.updateItem(item.getId(), item);
            prestamoRepository.deleteById(id);
        }
    }
}
