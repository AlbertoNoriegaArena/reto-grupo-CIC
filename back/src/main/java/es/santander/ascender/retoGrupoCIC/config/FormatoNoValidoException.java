package es.santander.ascender.retoGrupoCIC.config;

public class FormatoNoValidoException extends RuntimeException {
    private Long formatoId;
    private Long tipoItemId;
    private String formatoNombre;
    private String tipoItemNombre;
    
    public FormatoNoValidoException() {

    }

    public FormatoNoValidoException(String formatoNombre, String tipoItemNombre) {
        super("El formato con " + formatoNombre + " no es válido para el tipo de ítem con " + tipoItemNombre);
        this.formatoNombre = formatoNombre;
        this.tipoItemNombre = tipoItemNombre;
    }

    public Long getFormatoId() {
        return formatoId;
    }

    public Long getTipoItemId() {
        return tipoItemId;
    }


    public String getFormatoNombre() {
        return formatoNombre;
    }

    public String getTipoItemNombre() {
        return tipoItemNombre;
    }
   
}