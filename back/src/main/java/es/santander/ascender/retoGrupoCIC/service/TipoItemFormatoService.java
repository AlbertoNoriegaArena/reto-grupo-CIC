package es.santander.ascender.retoGrupoCIC.service;

import es.santander.ascender.retoGrupoCIC.model.Formato;
import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormato;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormatoId;
import es.santander.ascender.retoGrupoCIC.repository.TipoItemFormatoRepository;
import es.santander.ascender.retoGrupoCIC.repository.TipoItemRepository;
import es.santander.ascender.retoGrupoCIC.repository.FormatoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException; 

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TipoItemFormatoService {

    private TipoItemFormatoRepository tipoItemFormatoRepository;
    private TipoItemRepository tipoItemRepository;
    private FormatoRepository formatoRepository;

    @Autowired
    public TipoItemFormatoService(TipoItemFormatoRepository tipoItemFormatoRepository,
            TipoItemRepository tipoItemRepository, FormatoRepository formatoRepository) {
        this.tipoItemFormatoRepository = tipoItemFormatoRepository;
        this.tipoItemRepository = tipoItemRepository;
        this.formatoRepository = formatoRepository;
    }

    public TipoItemFormato createTipoItemFormato(Long tipoItemId, Long formatoId) {
        // Buscar TipoItem y Formato por sus IDs
        Optional<TipoItem> tipoItemOptional = tipoItemRepository.findById(tipoItemId);
        Optional<Formato> formatoOptional = formatoRepository.findById(formatoId);

        // Verificar si los TipoItem y Formato existen
        if (!tipoItemOptional.isPresent() || !formatoOptional.isPresent()) {
            throw new IllegalArgumentException("TipoItem o Formato no encontrado");
        }

        TipoItem tipoItem = tipoItemOptional.get();
        Formato formato = formatoOptional.get();
        // Crear una instancia de TipoItemFormatoId
        TipoItemFormatoId id = new TipoItemFormatoId(tipoItem.getId(), formato.getId());

        // Crear una instancia de TipoItemFormato
        TipoItemFormato tipoItemFormato = new TipoItemFormato();
        tipoItemFormato.setId(id); // Asignar la clave compuesta
        tipoItemFormato.setTipoItem(tipoItem);
        tipoItemFormato.setFormato(formato);

        return tipoItemFormatoRepository.save(tipoItemFormato);
    }

    @Transactional(readOnly = true)
    public List<TipoItemFormato> obtenerTipoItemFormatos() {
        return tipoItemFormatoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<TipoItemFormato> obtenerTipoItemFormatoPorId(TipoItemFormatoId id) {
        return tipoItemFormatoRepository.findById(id);
    }

    public TipoItemFormato updateTipoItemFormato(TipoItemFormatoId id, TipoItemFormato tipoItemFormatoActualizado) {
        if (tipoItemFormatoRepository.existsById(id)) {
            tipoItemFormatoActualizado.setId(id);
            return tipoItemFormatoRepository.save(tipoItemFormatoActualizado);
        }
        return null;
    }

    public String deleteTipoItemFormato(Long tipoItemId, Long formatoId) {
        // Crear una instancia de TipoItemFormatoId
        TipoItemFormatoId id = new TipoItemFormatoId(tipoItemId, formatoId);
        Optional<TipoItemFormato> tipoItemFormato = tipoItemFormatoRepository.findById(id);
        if (tipoItemFormato.isPresent()) {
            tipoItemFormatoRepository.deleteById(id);
            return "TipoItemFormato eliminado con éxito";
        }
        return "El TipoItemFormato no existe";
    }

    public Set<TipoItemFormato> obtenerTipoItemFormatosPorTipoItem(TipoItem tipoItem) {
        return tipoItemFormatoRepository.findByTipoItem(tipoItem);
    }

     /**
     * Elimina la relación entre un TipoItem y un Formato.
     * Lanza IllegalArgumentException si la relación no existe.
     *
     * @param tipoItemId ID del TipoItem.
     * @param formatoId ID del Formato.
     * @throws IllegalArgumentException si la relación no se encuentra.
     */
    public void eliminarRelacion(Long tipoItemId, Long formatoId) {
        TipoItemFormatoId id = new TipoItemFormatoId(tipoItemId, formatoId);
        // Usamos existsById para una verificación más eficiente antes de intentar borrar
        if (!tipoItemFormatoRepository.existsById(id)) {
             throw new IllegalArgumentException("La relación TipoItemFormato entre TipoItem " + tipoItemId + " y Formato " + formatoId + " no existe.");
        }
        try {
            tipoItemFormatoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
             throw new IllegalArgumentException("La relación TipoItemFormato entre TipoItem " + tipoItemId + " y Formato " + formatoId + " no existe (o fue eliminada concurrentemente).");
        }
    }

    @Transactional(readOnly = true)
    public List<TipoItemFormato> obtenerTipoItemFormatosPorFormatoId(Long formatoId) {
        // Validar que el formato exista podría ser una buena adición
        if (!formatoRepository.existsById(formatoId)) {
             throw new IllegalArgumentException("Formato no encontrado con ID: " + formatoId);
        }
        return tipoItemFormatoRepository.findByFormatoId(formatoId);
    }
}
