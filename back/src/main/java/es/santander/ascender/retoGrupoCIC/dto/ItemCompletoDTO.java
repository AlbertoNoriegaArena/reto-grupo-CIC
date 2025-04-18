package es.santander.ascender.retoGrupoCIC.dto;

import es.santander.ascender.retoGrupoCIC.model.EstadoItem;
import es.santander.ascender.retoGrupoCIC.model.Formato;
import es.santander.ascender.retoGrupoCIC.model.TipoItem;
import java.time.LocalDate;


public class ItemCompletoDTO {
    private Long id;
    private String nombre;
    private String ubicacion;
    private LocalDate fecha; 
    private EstadoItem estado;
    private TipoItem tipo;  
    private Formato formato; 

    // Libro
    private String autor;
    private String isbn;
    private String editorial;
    private Integer numeroPaginas;
    private LocalDate fechaPublicacion;

    // Pelicula
    private String director;
    private Integer duracionPelicula;
    private String generoPelicula;
    private LocalDate fechaEstreno;

    // Musica
    private String generoMusica;
    private String cantante;
    private String album;
    private String duracionMusica;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoItem getTipo() {
        return tipo;
    }

    public void setTipo(TipoItem tipo) {
        this.tipo = tipo;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getDuracionPelicula() {
        return duracionPelicula;
    }

    public void setDuracionPelicula(Integer duracionPelicula) {
        this.duracionPelicula = duracionPelicula;
    }

    public String getGeneroPelicula() {
        return generoPelicula;
    }

    public void setGeneroPelicula(String generoPelicula) {
        this.generoPelicula = generoPelicula;
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(LocalDate fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public String getGeneroMusica() {
        return generoMusica;
    }

    public void setGeneroMusica(String generoMusica) {
        this.generoMusica = generoMusica;
    }

    public String getCantante() {
        return cantante;
    }

    public void setCantante(String cantante) {
        this.cantante = cantante;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuracionMusica() {
        return duracionMusica;
    }

    public void setDuracionMusica(String duracionMusica) {
        this.duracionMusica = duracionMusica;
    }
}
