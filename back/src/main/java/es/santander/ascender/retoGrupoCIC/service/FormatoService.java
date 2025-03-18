package es.santander.ascender.retoGrupoCIC.service;

import es.santander.ascender.retoGrupoCIC.model.Formato;
import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import es.santander.ascender.retoGrupoCIC.model.TipoItemFormato;
import es.santander.ascender.retoGrupoCIC.repository.FormatoRepository;
import es.santander.ascender.retoGrupoCIC.repository.TipoItemFormatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Formato createFormato(Formato formato) {
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

    public Formato updateFormato(Long id, Formato formatoActualizado) {
        if (formatoRepository.existsById(id)) {
            formatoActualizado.setId(id);
            return formatoRepository.save(formatoActualizado);
        }
        return null;
    }

    public String deleteFormato(Long id) {
        Optional<Formato> formato = formatoRepository.findById(id);
        if (formato.isPresent()) {
            formatoRepository.deleteById(id);
            return "Formato eliminado con éxito";
        }
        return "El formato no existe";
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
