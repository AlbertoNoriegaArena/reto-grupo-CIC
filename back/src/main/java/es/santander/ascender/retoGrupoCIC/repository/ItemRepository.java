package es.santander.ascender.retoGrupoCIC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.santander.ascender.retoGrupoCIC.model.EstadoItem;
import es.santander.ascender.retoGrupoCIC.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByNombreContainingIgnoreCaseAndTipo_NombreAndEstadoAndUbicacion(
            String nombre, String tipoNombre, EstadoItem estado, String ubicacion);

    // Query busqueda de item sin tipo
    List<Item> findByNombreContainingIgnoreCaseAndEstadoAndUbicacion(
            String nombre, EstadoItem estado, String ubicacion);

    // Query busqueda de item sin estado
    List<Item> findByNombreContainingIgnoreCaseAndTipo_NombreAndUbicacion(
            String nombre, String tipoNombre, String ubicacion);

    // Query busqueda de item sin ubicacion
    List<Item> findByNombreContainingIgnoreCaseAndTipo_NombreAndEstado(
            String nombre, String tipoNombre, EstadoItem estado);

    // Query busqueda de item sin tipo y estado
    List<Item> findByNombreContainingIgnoreCaseAndUbicacion(
            String nombre, String ubicacion);

    // Query busqueda de item sin tipo y ubicacion
    List<Item> findByNombreContainingIgnoreCaseAndEstado(
            String nombre, EstadoItem estado);

    // Query busqueda de item sin estado y ubicacion
    List<Item> findByNombreContainingIgnoreCaseAndTipo_Nombre(
            String nombre, String tipoNombre);

    // Query busqueda de item solo por nombre
    List<Item> findByNombreContainingIgnoreCase(String nombre);

    // Query busqueda de item solo por tipo
    List<Item> findByTipo_Nombre(String tipoNombre);

    // Query busqueda de item solo por estado
    List<Item> findByEstado(EstadoItem estado);

    // Query busqueda de item solo por ubicacion
    List<Item> findByUbicacion(String ubicacion);

    // Query busqueda de item solo por tipo y estado
    List<Item> findByTipo_NombreAndEstado(String tipoNombre, EstadoItem estado);

    // Query busqueda de item solo por tipo y ubicacion
    List<Item> findByTipo_NombreAndUbicacion(String tipoNombre, String ubicacion);

    // Query busqueda de item solo por ubicación y estado
    List<Item> findByEstadoAndUbicacion(EstadoItem estado, String ubicacion);

    // Query busqueda de item solo por tipo y estado y ubiacion
    List<Item> findByTipo_NombreAndEstadoAndUbicacion(String tipoNombre, EstadoItem estado, String ubicacion);

    List<Item> findAll();
}
