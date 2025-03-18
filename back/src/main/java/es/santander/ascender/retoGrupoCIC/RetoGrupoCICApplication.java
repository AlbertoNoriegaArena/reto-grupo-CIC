package es.santander.ascender.retoGrupoCIC;

import es.santander.ascender.retoGrupoCIC.model.*;
import es.santander.ascender.retoGrupoCIC.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class RetoGrupoCICApplication {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TipoItemRepository tipoItemRepository;

    @Autowired
    private FormatoRepository formatoRepository;

    // @Autowired
    // private LibroRepository libroRepository;

    // @Autowired
    // private MusicaRepository musicaRepository;

    // @Autowired
    // private PeliculaRepository peliculaRepository;

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

            // Crear y guardar 10 Items
            //Musica
            Item item1 = new Item(null, "Thriller - Michael Jackson", tipoMusica, formatoCD, "Estante B", LocalDate.of(1982, 11, 30), EstadoItem.PRESTADO);
            itemRepository.save(item1);
            // Musica musica1 = new Musica(item1.getId(), "123456", "Epic Records", 12, item1);
            // musicaRepository.save(musica1);

            Item item2 = new Item(null, "Bohemian Rhapsody - Queen", tipoMusica, formatoVinilo, "Estante D", LocalDate.of(1975, 10, 31), EstadoItem.DISPONIBLE);
            itemRepository.save(item2);
            // Musica musica2 = new Musica(item2.getId(), "78910", "EMI Records", 15, item2);
            // musicaRepository.save(musica2);

            Item item3 = new Item(null, "The Dark Side of the Moon - Pink Floyd", tipoMusica, formatoCassette, "Estante E", LocalDate.of(1973, 3, 1), EstadoItem.DISPONIBLE);
            itemRepository.save(item3);
            // Musica musica3 = new Musica(item3.getId(), "111213", "Harvest Records", 10, item3);
            // musicaRepository.save(musica3);

            //Peliculas
            Item item4 = new Item(null, "Interstellar", tipoPelicula, formatoDVD, "Estante C", LocalDate.of(2014, 10, 26), EstadoItem.DISPONIBLE);
            itemRepository.save(item4);
            // Pelicula pelicula1 = new Pelicula(item4.getId(), "Christopher Nolan", 169, "Ciencia ficción", LocalDate.of(2014, 10, 26), item4);
            // peliculaRepository.save(pelicula1);

            Item item5 = new Item(null, "Matrix", tipoPelicula, formatoBluRay, "Estante F", LocalDate.of(1999, 3, 31), EstadoItem.DISPONIBLE);
            itemRepository.save(item5);
            // Pelicula pelicula2 = new Pelicula(item5.getId(), "Lana Wachowski", 136, "Ciencia ficción", LocalDate.of(1999, 3, 31), item5);
            // peliculaRepository.save(pelicula2);

            Item item6 = new Item(null, "El silencio de los corderos", tipoPelicula, formatoVHS, "Estante G", LocalDate.of(1991, 2, 14), EstadoItem.DISPONIBLE);
            itemRepository.save(item6);
            // Pelicula pelicula3 = new Pelicula(item6.getId(), "Jonathan Demme", 118, "Thriller", LocalDate.of(1991, 2, 14), item6);
            // peliculaRepository.save(pelicula3);
            
            //Libros
            Item item7 = new Item(null, "El Señor de los Anillos", tipoLibro, formatoFisico, "Estante A", LocalDate.of(1954, 7, 29), EstadoItem.DISPONIBLE);
            itemRepository.save(item7);
            // Libro libro1 = new Libro(item7.getId(), "978-84-450-7179-3", "Minotauro", 1200, item7);
            // libroRepository.save(libro1);

            Item item8 = new Item(null, "1984 - George Orwell", tipoLibro, formatoDigital, "Online", LocalDate.of(1949, 6, 8), EstadoItem.PERDIDO);
            itemRepository.save(item8);
            // Libro libro2 = new Libro(item8.getId(), "978-0-451-52493-5", "Signet Classics", 328, item8);
            // libroRepository.save(libro2);

            Item item9 = new Item(null, "Cien años de soledad", tipoLibro, formatoFisico, "Estante H", LocalDate.of(1967, 5, 30), EstadoItem.DISPONIBLE);
            itemRepository.save(item9);
            // Libro libro3 = new Libro(item9.getId(), "978-0307350753", "Debolsillo", 417, item9);
            // libroRepository.save(libro3);

            Item item10 = new Item(null, "El Hobbit", tipoLibro, formatoFisico, "Estante I", LocalDate.of(1937, 9, 21), EstadoItem.DISPONIBLE);
            itemRepository.save(item10);
            // Libro libro4 = new Libro(item10.getId(), "978-0547928227", "Houghton Mifflin Harcourt", 310, item10);
            // libroRepository.save(libro4);
        };
    }
}
