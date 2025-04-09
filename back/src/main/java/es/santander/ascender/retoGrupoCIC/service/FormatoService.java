package es.santander.ascender.retoGrupoCIC.service;

import es.santander.ascender.retoGrupoCIC.config.FormatoNotFoundException;
import es.santander.ascender.retoGrupoCIC.dto.FormatoDTO;
import es.santander.ascender.retoGrupoCIC.model.Formato;
import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormato;
import es.santander.ascender.retoGrupoCIC.repository.FormatoRepository;
import es.santander.ascender.retoGrupoCIC.repository.TipoItemFormatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException; 

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class FormatoService {

    private FormatoRepository formatoRepository;
    private TipoItemFormatoRepository tipoItemFormatoRepository;

    @Autowired
    public FormatoService(FormatoRepository formatoRepository, TipoItemFormatoRepository tipoItemFormatoRepository) {
        this.formatoRepository = formatoRepository;
        this.tipoItemFormatoRepository = tipoItemFormatoRepository;
    }

    public Formato createFormato(FormatoDTO formatoDTO) {
        // Validar que el nombre no esté vacío
        if (formatoDTO.getNombre() == null || formatoDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del formato no puede estar vacío");
        }
        // Validar que el nombre no exista ya
        if (formatoRepository.findByNombre(formatoDTO.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un formato con ese nombre");
        }
        Formato formato = new Formato();
        formato.setNombre(formatoDTO.getNombre());
        return formatoRepository.save(formato);
    }

    @Transactional(readOnly = true)
    public List<Formato> obtenerFormatos() {
        return formatoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Formato> obtenerFormatoPorId(Long id) {
        return formatoRepository.findById(id);
    }

    public Formato updateFormato(Long id, FormatoDTO formatoDTO) {
        Optional<Formato> formatoOptional = formatoRepository.findById(id);
        if (formatoOptional.isPresent()) {
            Formato formatoExistente = formatoOptional.get();
            // Validar que el nombre no esté vacío
            if (formatoDTO.getNombre() == null || formatoDTO.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del formato no puede estar vacío");
            }
            // Validar que el nombre no exista ya
            Optional<Formato> formatoConMismoNombre = formatoRepository.findByNombre(formatoDTO.getNombre());
            if (formatoConMismoNombre.isPresent() && !formatoConMismoNombre.get().getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe un formato con ese nombre");
            }
            formatoExistente.setNombre(formatoDTO.getNombre());
            return formatoRepository.save(formatoExistente);
        }
        return null;
    }

    public void deleteFormato(Long id) {
    
        if (!formatoRepository.existsById(id)) {
            throw new FormatoNotFoundException(id);
        }
        try {
            formatoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            // Captura por si acaso existe una condición de carrera
             throw new FormatoNotFoundException(id, "El formato no existe o fue eliminado concurrentemente");
        }
    }

    // Método para obtener los formatos válidos para un TipoItem específico
    @Transactional(readOnly = true)
    public List<Formato> obtenerFormatosPorTipoItem(TipoItem tipoItem) {
        Set<TipoItemFormato> tipoItemFormatos = tipoItemFormatoRepository.findByTipoItem(tipoItem);
        return tipoItemFormatos.stream()
                .map(TipoItemFormato::getFormato)
                .collect(Collectors.toList());
    }
}
