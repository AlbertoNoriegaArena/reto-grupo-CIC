package es.santander.ascender.retoGrupoCIC.config;

import java.util.Map;

public class ErrorInfo {

    private int codigo;
    private String mensaje;
    private Map<String, String> parametros;

    public ErrorInfo(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public ErrorInfo(int codigo, String mensaje, Map<String, String> parametros) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.parametros = parametros;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Map<String, String> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, String> parametros) {
        this.parametros = parametros;
    }
}