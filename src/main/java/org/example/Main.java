package org.example;

import com.github.javafaker.Faker;
import org.example.dao.CatalogoDAO;
import org.example.entities.Libro;
import org.example.entities.Rivista;
import org.example.enumeration.Periodicita;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("catalogoPU");
        EntityManager em = emf.createEntityManager();
        CatalogoDAO catalogoDAO = new CatalogoDAO(em);


        Faker faker = new Faker();

        try {
            // Aggiunta di un libro
            Libro libro = new Libro(
                    faker.code().isbn10(),
                    faker.book().title(),
                    LocalDate.now().getYear() - faker.number().numberBetween(1, 50),
                    faker.number().numberBetween(100, 1000),
                    faker.book().author(),
                    faker.book().genre()
            );
            catalogoDAO.aggiungiElemento(libro);
            System.out.println("Libro aggiunto: " + libro);

            // Aggiunta di una rivista
            Rivista rivista = new Rivista(
                    faker.code().isbn10(),
                    faker.lorem().sentence(3),
                    LocalDate.now().getYear() - faker.number().numberBetween(1, 20),
                    faker.number().numberBetween(30, 200),
                    Periodicita.values()[faker.number().numberBetween(0, Periodicita.values().length)]
            );
            catalogoDAO.aggiungiElemento(rivista);
            System.out.println("Rivista aggiunta: " + rivista);

            // Ricerca per ISBN
            String isbnDaCercare = libro.getCodiceISBN();
            System.out.println("Ricerca per ISBN (" + isbnDaCercare + "): " +
                    catalogoDAO.ricercaPerISBN(isbnDaCercare));

            // Ricerca per anno di pubblicazione
            int annoPubblicazione = libro.getAnnoPubblicazione();
            System.out.println("Ricerca per anno (" + annoPubblicazione + "): " +
                    catalogoDAO.ricercaPerAnno(annoPubblicazione));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}

