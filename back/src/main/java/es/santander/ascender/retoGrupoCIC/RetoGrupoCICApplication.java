package es.santander.ascender.retoGrupoCIC;

import es.santander.ascender.retoGrupoCIC.model.*;
import es.santander.ascender.retoGrupoCIC.repository.*;
import es.santander.ascender.retoGrupoCIC.service.ItemService;
import es.santander.ascender.retoGrupoCIC.service.PrestamoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class RetoGrupoCICApplication {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TipoItemRepository tipoItemRepository;

    @Autowired
    private FormatoRepository formatoRepository;

    @Autowired
    private TipoItemFormatoRepository tipoItemFormatoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PrestamoService prestamoService;

    public static void main(String[] args) {
        SpringApplication.run(RetoGrupoCICApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {
            // Crear TipoItem (si no existen)
            TipoItem tipoMusica = tipoItemRepository.findByNombre("Musica").orElseGet(() -> {
                TipoItem newTipoMusica = new TipoItem(null, "Musica");
                return tipoItemRepository.save(newTipoMusica);
            });

            TipoItem tipoLibro = tipoItemRepository.findByNombre("Libro").orElseGet(() -> {
                TipoItem newTipoLibro = new TipoItem(null, "Libro");
                return tipoItemRepository.save(newTipoLibro);
            });

            TipoItem tipoPelicula = tipoItemRepository.findByNombre("Pelicula").orElseGet(() -> {
                TipoItem newTipoPelicula = new TipoItem(null, "Pelicula");
                return tipoItemRepository.save(newTipoPelicula);
            });

            // Crear Formatos (si no existen)
            Formato formatoCD = formatoRepository.findByNombre("CD").orElseGet(() -> {
                Formato newFormatoCD = new Formato(null, "CD");
                return formatoRepository.save(newFormatoCD);
            });

            Formato formatoCassette = formatoRepository.findByNombre("Cassette").orElseGet(() -> {
                Formato newFormatoCassette = new Formato(null, "Cassette");
                return formatoRepository.save(newFormatoCassette);
            });

            Formato formatoVinilo = formatoRepository.findByNombre("Vinilo").orElseGet(() -> {
                Formato newFormatoVinilo = new Formato(null, "Vinilo");
                return formatoRepository.save(newFormatoVinilo);
            });

            Formato formatoVHS = formatoRepository.findByNombre("VHS").orElseGet(() -> {
                Formato newFormatoVHS = new Formato(null, "VHS");
                return formatoRepository.save(newFormatoVHS);
            });

            Formato formatoDVD = formatoRepository.findByNombre("DVD").orElseGet(() -> {
                Formato newFormatoDVD = new Formato(null, "DVD");
                return formatoRepository.save(newFormatoDVD);
            });

            Formato formatoBluRay = formatoRepository.findByNombre("Blu-ray").orElseGet(() -> {
                Formato newFormatoBluRay = new Formato(null, "Blu-ray");
                return formatoRepository.save(newFormatoBluRay);
            });

            Formato formatoFisico = formatoRepository.findByNombre("Fisico").orElseGet(() -> {
                Formato newFormatoFisico = new Formato(null, "Fisico");
                return formatoRepository.save(newFormatoFisico);
            });

            Formato formatoDigital = formatoRepository.findByNombre("Digital").orElseGet(() -> {
                Formato newFormatoDigital = new Formato(null, "Digital");
                return formatoRepository.save(newFormatoDigital);
            });

            // Crear las relaciones TipoItemFormato (si no existen)
            // Musica
            createTipoItemFormatoIfNotExists(tipoMusica, formatoCD);
            createTipoItemFormatoIfNotExists(tipoMusica, formatoCassette);
            createTipoItemFormatoIfNotExists(tipoMusica, formatoVinilo);

            // Pelicula
            createTipoItemFormatoIfNotExists(tipoPelicula, formatoDVD);
            createTipoItemFormatoIfNotExists(tipoPelicula, formatoBluRay);
            createTipoItemFormatoIfNotExists(tipoPelicula, formatoVHS);

            // Libro
            createTipoItemFormatoIfNotExists(tipoLibro, formatoFisico);
            createTipoItemFormatoIfNotExists(tipoLibro, formatoDigital);

            // Crear y guardar 12 Items
            // Musica
            Item item1 = new Item(null, "Thriller - Michael Jackson", tipoMusica, formatoCD, "Estante B",
                    LocalDate.of(1982, 11, 30), EstadoItem.DISPONIBLE);
            Item item2 = new Item(null, "Bohemian Rhapsody - Queen", tipoMusica, formatoVinilo, "Estante C",
                    LocalDate.of(1975, 10, 31), EstadoItem.DISPONIBLE);
            Item item3 = new Item(null, "The Dark Side of the Moon - Pink Floyd", tipoMusica, formatoCassette,
                    "Estante C", LocalDate.of(1973, 3, 1), EstadoItem.DISPONIBLE);

            // Peliculas
            Item item4 = new Item(null, "Interstellar", tipoPelicula, formatoDVD, "Estante C",
                    LocalDate.of(2014, 10, 26), EstadoItem.DISPONIBLE);
            Item item5 = new Item(null, "Matrix", tipoPelicula, formatoBluRay, "Estante A", LocalDate.of(1999, 3, 31),
                    EstadoItem.DISPONIBLE);
            Item item6 = new Item(null, "El silencio de los corderos", tipoPelicula, formatoVHS, "Estante A",
                    LocalDate.of(1991, 2, 14), EstadoItem.DISPONIBLE);

            // Libros
            Item item7 = new Item(null, "El Señor de los Anillos", tipoLibro, formatoFisico, "Estante A",
                    LocalDate.of(1954, 7, 29), EstadoItem.DISPONIBLE);
            Item item8 = new Item(null, "1984 - George Orwell", tipoLibro, formatoDigital, "Online",
                    LocalDate.of(1949, 6, 8), EstadoItem.DISPONIBLE);
            Item item9 = new Item(null, "Cien años de soledad", tipoLibro, formatoFisico, "Estante B",
                    LocalDate.of(1967, 5, 30), EstadoItem.DISPONIBLE);
            Item item10 = new Item(null, "El Hobbit", tipoLibro, formatoFisico, "Estante B", LocalDate.of(1937, 9, 21),
                    EstadoItem.DISPONIBLE);

            List<Item> items = itemRepository
                    .saveAll(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10));

            // Crear Personas
            Persona persona1 = new Persona(null, "Juan Pérez", "Calle Mayor 1", "juan@example.com", "123456789");
            Persona persona2 = new Persona(null, "María López", "Calle Sol 2", "maria@example.com", "987654321");
            Persona persona3 = new Persona(null, "Carlos García", "Avenida del Sol 15", "carlos@example.com",
                    "654987321");
            Persona persona4 = new Persona(null, "Laura Martínez", "Plaza de la Luna 8", "laura@example.com",
                    "963852741");
            Persona persona5 = new Persona(null, "Pedro Rodríguez", "Calle Estrella 22", "pedro@example.com",
                    "666555444");
            personaRepository.saveAll(Arrays.asList(persona1, persona2, persona3, persona4, persona5));

            List<Persona> personas = personaRepository
                    .saveAll(Arrays.asList(persona1, persona2, persona3, persona4, persona5));

            // Crear Prestamos
            Prestamo prestamo1 = new Prestamo(null, items.get(0), personas.get(0), LocalDate.now(), null,
                    LocalDate.now().plusDays(15));
            prestamoService.createPrestamo(prestamo1);

            Prestamo prestamo2 = new Prestamo(null, items.get(1), personas.get(1), LocalDate.now(), null,
                    LocalDate.now().plusDays(7));
            prestamoService.createPrestamo(prestamo2);
            Prestamo prestamo3 = new Prestamo(null, items.get(2), personas.get(2), LocalDate.now(), null,
                    LocalDate.now().plusDays(10));
            prestamoService.createPrestamo(prestamo3);
            Prestamo prestamo4 = new Prestamo(null, items.get(3), personas.get(3), LocalDate.now(), null,
                    LocalDate.now().plusDays(14));
            prestamoService.createPrestamo(prestamo4);
            Prestamo prestamo5 = new Prestamo(null, items.get(4), personas.get(0), LocalDate.now(), null,
                    LocalDate.now().plusDays(21));
            prestamoService.createPrestamo(prestamo5);
            Prestamo prestamo6 = new Prestamo(null, items.get(5), personas.get(0), LocalDate.now(), null,
                    LocalDate.now().plusDays(7));
            prestamoService.createPrestamo(prestamo6);
            Prestamo prestamo7 = new Prestamo(null, items.get(6), personas.get(1), LocalDate.now(), null,
                    LocalDate.now().plusDays(15));
            prestamoService.createPrestamo(prestamo7);
            Prestamo prestamo8 = new Prestamo(null, items.get(7), personas.get(1), LocalDate.now(), null,
                    LocalDate.now().plusDays(10));
            prestamoService.createPrestamo(prestamo8);
            Prestamo prestamo9 = new Prestamo(null, items.get(8), personas.get(3), LocalDate.now(), null,
                    LocalDate.now().plusDays(14));
            prestamoService.createPrestamo(prestamo9);
            // Prestamo prestamo10 = new Prestamo(null, items.get(9), personas.get(2),
            // LocalDate.now(), null,
            // LocalDate.now().plusDays(21));
            // prestamoService.createPrestamo(prestamo10);

            prestamoRepository.saveAll(Arrays.asList(prestamo1, prestamo2, prestamo3, prestamo4, prestamo5, prestamo6,
                    prestamo7, prestamo8, prestamo9));
        };
    }

    // Método auxiliar para crear TipoItemFormato si no existe
    private void createTipoItemFormatoIfNotExists(TipoItem tipoItem, Formato formato) {
        TipoItemFormatoId id = new TipoItemFormatoId(tipoItem.getId(), formato.getId());
        if (!tipoItemFormatoRepository.existsById(id)) {
            TipoItemFormato tipoItemFormato = new TipoItemFormato();
            tipoItemFormato.setId(id);
            tipoItemFormato.setTipoItem(tipoItem);
            tipoItemFormato.setFormato(formato);
            tipoItemFormatoRepository.save(tipoItemFormato);
        }
    }
}
