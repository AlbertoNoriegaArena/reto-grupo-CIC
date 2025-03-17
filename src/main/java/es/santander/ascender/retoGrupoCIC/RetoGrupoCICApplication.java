package es.santander.ascender.retoGrupoCIC;

import es.santander.ascender.retoGrupoCIC.model.*;
import es.santander.ascender.retoGrupoCIC.repository.ItemRepository;
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

    public static void main(String[] args) {
        SpringApplication.run(RetoGrupoCICApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {
            // Crear y guardar 5 Items con tipos válidos
            Item item1 = new Item(null, "El Señor de los Anillos", null, null, "Estante A", LocalDate.of(1954, 7, 29), EstadoItem.DISPONIBLE);
            Item item2 = new Item(null, "Thriller - Michael Jackson", null, null, "Estante B", LocalDate.of(1982, 11, 30), EstadoItem.PRESTADO);
            Item item3 = new Item(null, "Interstellar", null, null, "Estante C", LocalDate.of(2014, 10, 26), EstadoItem.DISPONIBLE);
            Item item4 = new Item(null, "1984 - George Orwell", null, null, "Online", LocalDate.of(1949, 6, 8), EstadoItem.PERDIDO);
            Item item5 = new Item(null, "Bohemian Rhapsody - Queen", null, null, "Estante D", LocalDate.of(1975, 10, 31), EstadoItem.DISPONIBLE);

            itemRepository.save(item1);
            itemRepository.save(item2);
            itemRepository.save(item3);
            itemRepository.save(item4);
            itemRepository.save(item5);
        };
    }
}
