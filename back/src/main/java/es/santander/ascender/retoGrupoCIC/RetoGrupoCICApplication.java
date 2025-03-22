package es.santander.ascender.retoGrupoCIC;

import es.santander.ascender.retoGrupoCIC.model.*;
import es.santander.ascender.retoGrupoCIC.repository.*;
import es.santander.ascender.retoGrupoCIC.service.PrestamoService;
import jakarta.transaction.Transactional;

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

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;
      

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

            // Musica
            Item itemMusica1 = new Item(null, "Thriller - Michael Jackson", tipoMusica, formatoCD, "Estante B",
                    LocalDate.of(1982, 11, 30), EstadoItem.DISPONIBLE);
            Item itemMusica2 = new Item(null, "Bohemian Rhapsody - Queen", tipoMusica, formatoVinilo, "Estante C",
                    LocalDate.of(1975, 10, 31), EstadoItem.DISPONIBLE);
            Item itemMusica3 = new Item(null, "The Dark Side of the Moon - Pink Floyd", tipoMusica, formatoCassette,
                    "Estante C", LocalDate.of(1973, 3, 1), EstadoItem.DISPONIBLE);
            Item itemMusica4 = new Item(null, "Back in Black - AC/DC", tipoMusica, formatoVinilo, "Estante A",
                    LocalDate.of(1980, 7, 25), EstadoItem.DISPONIBLE);
            Item itemMusica5 = new Item(null, "Nevermind - Nirvana", tipoMusica, formatoCD, "Estante B",
                    LocalDate.of(1991, 9, 24), EstadoItem.DISPONIBLE);

            // Peliculas
            Item itemPelicula1 = new Item(null, "Interstellar", tipoPelicula, formatoDVD, "Estante C",
                    LocalDate.of(2014, 10, 26), EstadoItem.DISPONIBLE);
            Item itemPelicula2 = new Item(null, "Matrix", tipoPelicula, formatoBluRay, "Estante A", LocalDate.of(1999, 3, 31),
                    EstadoItem.DISPONIBLE);
            Item itemPelicula3 = new Item(null, "El silencio de los corderos", tipoPelicula, formatoVHS, "Estante A",
                    LocalDate.of(1991, 2, 14), EstadoItem.DISPONIBLE);
            Item itemPelicula4 = new Item(null, "El Padrino", tipoPelicula, formatoDVD, "Estante B",
                    LocalDate.of(1972, 3, 24), EstadoItem.DISPONIBLE);
            Item itemPelicula5 = new Item(null, "Pulp Fiction", tipoPelicula, formatoBluRay, "Estante C",
                    LocalDate.of(1994, 10, 14), EstadoItem.DISPONIBLE);

            // Libros
            Item itemLibro1 = new Item(null, "El Señor de los Anillos", tipoLibro, formatoFisico, "Estante A",
                    LocalDate.of(1954, 7, 29), EstadoItem.DISPONIBLE);
            Item itemLibro2 = new Item(null, "1984 - George Orwell", tipoLibro, formatoDigital, "Online",
                    LocalDate.of(1949, 6, 8), EstadoItem.DISPONIBLE);
            Item itemLibro3 = new Item(null, "Cien años de soledad", tipoLibro, formatoFisico, "Estante B",
                    LocalDate.of(1967, 5, 30), EstadoItem.DISPONIBLE);
            Item itemLibro4 = new Item(null, "El Hobbit", tipoLibro, formatoFisico, "Estante B", LocalDate.of(1937, 9, 21),
                    EstadoItem.DISPONIBLE);
            Item itemLibro5 = new Item(null, "Crimen y Castigo", tipoLibro, formatoFisico, "Estante C",
                    LocalDate.of(1866, 1, 1), EstadoItem.DISPONIBLE);

            //Guardamos los items
            List<Item> items = Arrays.asList(
                    itemMusica1, itemMusica2, itemMusica3,itemMusica4,itemMusica5,
                    itemPelicula1, itemPelicula2, itemPelicula3,itemPelicula4,itemPelicula5,
                    itemLibro1, itemLibro2, itemLibro3, itemLibro4,itemLibro5
            );

            // Musica
            Musica musica1 = new Musica();
            musica1.setItem(itemMusica1);
            musica1.setCantante("Michael Jackson");
            musica1.setGenero("Pop");
            musica1.setAlbum("Thriller");
            musica1.setDuracion("42:19");

            Musica musica2 = new Musica();
            musica2.setItem(itemMusica2);
            musica2.setCantante("Queen");
            musica2.setGenero("Rock");
            musica2.setAlbum("A Night at the Opera");
            musica2.setDuracion("5:55");

            Musica musica3 = new Musica();
            musica3.setItem(itemMusica3);
            musica3.setCantante("Pink Floyd");
            musica3.setGenero("Rock Progresivo");
            musica3.setAlbum("The Dark Side of the Moon");
            musica3.setDuracion("42:50");

            Musica musica4 = new Musica();
            musica4.setItem(itemMusica4);
            musica4.setCantante("AC/DC");
            musica4.setGenero("Hard Rock");
            musica4.setAlbum("Back in Black");
            musica4.setDuracion("42:11");

            Musica musica5 = new Musica();
            musica5.setItem(itemMusica5);
            musica5.setCantante("Nirvana");
            musica5.setGenero("Grunge");
            musica5.setAlbum("Nevermind");
            musica5.setDuracion("49:20");

            // Peliculas
            Pelicula pelicula1 = new Pelicula();
            pelicula1.setItem(itemPelicula1);
            pelicula1.setDirector("Christopher Nolan");
            pelicula1.setGenero("Ciencia Ficción");
            pelicula1.setDuracion(169);
            pelicula1.setFechaEstreno(LocalDate.of(2014, 11, 7));

            Pelicula pelicula2 = new Pelicula();
            pelicula2.setItem(itemPelicula2);
            pelicula2.setDirector("Lana Wachowski, Lilly Wachowski");
            pelicula2.setGenero("Ciencia Ficción");
            pelicula2.setDuracion(136);
            pelicula2.setFechaEstreno(LocalDate.of(1999, 3, 31));

            Pelicula pelicula3 = new Pelicula();
            pelicula3.setItem(itemPelicula3);
            pelicula3.setDirector("Jonathan Demme");
            pelicula3.setGenero("Thriller");
            pelicula3.setDuracion(118);
            pelicula3.setFechaEstreno(LocalDate.of(1991, 2, 14));

            Pelicula pelicula4 = new Pelicula();
            pelicula4.setItem(itemPelicula4);
            pelicula4.setDirector("Francis Ford Coppola");
            pelicula4.setGenero("Drama");
            pelicula4.setDuracion(175);
            pelicula4.setFechaEstreno(LocalDate.of(1972, 3, 24));

            Pelicula pelicula5 = new Pelicula();
            pelicula5.setItem(itemPelicula5);
            pelicula5.setDirector("Quentin Tarantino");
            pelicula5.setGenero("Crimen");
            pelicula5.setDuracion(154);
            pelicula5.setFechaEstreno(LocalDate.of(1994, 10, 14));

            // Libros
            Libro libro1 = new Libro();
            libro1.setItem(itemLibro1);
            libro1.setIsbn("978-84-450-7179-3");
            libro1.setEditorial("Minotauro");
            libro1.setNumeroPaginas(1200);

            Libro libro2 = new Libro();
            libro2.setItem(itemLibro2);
            libro2.setIsbn("978-84-233-5070-0");
            libro2.setEditorial("Destino");
            libro2.setNumeroPaginas(328);

            Libro libro3 = new Libro();
            libro3.setItem(itemLibro3);
            libro3.setIsbn("978-84-376-0494-7");
            libro3.setEditorial("Cátedra");
            libro3.setNumeroPaginas(471);

            Libro libro4 = new Libro();
            libro4.setItem(itemLibro4);
            libro4.setIsbn("978-84-450-7149-6");
            libro4.setEditorial("Minotauro");
            libro4.setNumeroPaginas(310);

            Libro libro5 = new Libro();
            libro5.setItem(itemLibro5);
            libro5.setIsbn("978-84-206-5727-2");
            libro5.setEditorial("Alianza");
            libro5.setNumeroPaginas(672);
            
            //Guardamos las entidades especificas
            musicaRepository.saveAll(Arrays.asList(musica1,musica2,musica3,musica4,musica5));
            peliculaRepository.saveAll(Arrays.asList(pelicula1,pelicula2,pelicula3,pelicula4,pelicula5));
            libroRepository.saveAll(Arrays.asList(libro1,libro2,libro3,libro4,libro5));

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
