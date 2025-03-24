package es.santander.ascender.retoGrupoCIC.config;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ControllerAdviceConfig {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseBody
    public ErrorInfo handleItemNotFoundException(HttpServletRequest req, ItemNotFoundException ex) {
        return new ErrorInfo(1, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ItemPrestadoException.class)
    @ResponseBody
    public ErrorInfo handleItemPrestadoException(HttpServletRequest req, ItemPrestadoException ex) {
        return new ErrorInfo(2, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FormatoNoValidoException.class)
    @ResponseBody
    public ErrorInfo handleFormatoNoValidoException(HttpServletRequest req, FormatoNoValidoException ex) {
        return new ErrorInfo(3, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TipoItemNotFoundException.class)
    @ResponseBody
    public ErrorInfo handleTipoItemNotFoundException(HttpServletRequest req, TipoItemNotFoundException ex) {
        return new ErrorInfo(4, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FormatoNotFoundException.class)
    @ResponseBody
    public ErrorInfo handleFormatoNotFoundException(HttpServletRequest req, FormatoNotFoundException ex) {
        return new ErrorInfo(5, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PersonaNotFoundException.class)
    @ResponseBody
    public ErrorInfo handlePersonaNotFoundException(HttpServletRequest req, PersonaNotFoundException ex) {
        return new ErrorInfo(6, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PrestamoNotFoundException.class)
    @ResponseBody
    public ErrorInfo handlePrestamoNotFoundException(HttpServletRequest req, PrestamoNotFoundException ex) {
        return new ErrorInfo(7, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PrestamoBorradoException.class)
    @ResponseBody
    public ErrorInfo handlePrestamoBorradoException(HttpServletRequest req, PrestamoBorradoException ex) {
        return new ErrorInfo(8, "El préstamo con ID " + ex.getId() + " ya fue borrado");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ItemObligatorioException.class)
    @ResponseBody
    public ErrorInfo handleItemObligatorioException(HttpServletRequest req, ItemObligatorioException ex) {
        return new ErrorInfo(9, "El item es obligotorio");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PersonaObligatoriaException.class)
    @ResponseBody
    public ErrorInfo handlePersonaObligatoriaException(HttpServletRequest req, PersonaObligatoriaException ex) {
        return new ErrorInfo(10, "La persona es obligotoria");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FechaDevolucionInvalidaException.class)
    @ResponseBody
    public ErrorInfo handleFechaDevolucionInvalidaException(HttpServletRequest req,
            FechaDevolucionInvalidaException ex) {
        return new ErrorInfo(11, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ItemAsociadoAPrestamoException.class)
    @ResponseBody
    public ErrorInfo handleItemAsociadoAPrestamoException(HttpServletRequest req, ItemAsociadoAPrestamoException ex) {
        return new ErrorInfo(12, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PersonaAsociadaAPrestamoException.class)
    @ResponseBody
    public ErrorInfo handlePersonaAsociadaAPrestamoException(HttpServletRequest req,
            PersonaAsociadaAPrestamoException ex) {
        return new ErrorInfo(13, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomValidationException.class)
    @ResponseBody
    public ErrorInfo handleCustomValidationException(HttpServletRequest req, CustomValidationException ex) {

        return new ErrorInfo(14, "Error de validación", ex.getViolations());
    }

}
