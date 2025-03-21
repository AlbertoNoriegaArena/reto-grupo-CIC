package es.santander.ascender.retoGrupoCIC.dto;

import es.santander.ascender.retoGrupoCIC.model.EstadoItem;
import es.santander.ascender.retoGrupoCIC.model.Formato;
import es.santander.ascender.retoGrupoCIC.model.TipoItem;

import java.time.LocalDate;

public class ItemDTO {
    private String nombre;
    private Long tipoId;
    private Long formatoId;
    private String ubicacion;
    private LocalDate fecha;
    private EstadoItem estado;

    // Constructor, getters y setters

    public ItemDTO() {
    }

    public ItemDTO(String nombre, Long tipoId, Long formatoId, String ubicacion, LocalDate fecha, EstadoItem estado) {
        this.nombre = nombre;
        this.tipoId = tipoId;
        this.formatoId = formatoId;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public Long getFormatoId() {
        return formatoId;
    }

    public void setFormatoId(Long formatoId) {
        this.formatoId = formatoId;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoItem getEstado() {
        return estado;
    }

    public void setEstado(EstadoItem estado) {
        this.estado = estado;
    }
}
