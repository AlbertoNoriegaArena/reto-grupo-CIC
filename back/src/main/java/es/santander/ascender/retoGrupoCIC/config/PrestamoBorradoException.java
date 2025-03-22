package es.santander.ascender.retoGrupoCIC.config;

public class PrestamoBorradoException extends RuntimeException{

    private Long id;

    public PrestamoBorradoException(){

    }

    public PrestamoBorradoException(Long id) {
        super("El préstamo con ID " + id + " ya fue borrado");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
