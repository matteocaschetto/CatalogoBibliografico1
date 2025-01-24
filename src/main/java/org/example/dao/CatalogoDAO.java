package org.example.dao;

import org.example.entities.*;

import javax.persistence.EntityManager;
import java.util.List;

public class CatalogoDAO {

    private EntityManager em;

    public CatalogoDAO(EntityManager em) {
        this.em = em;
    }

    public void aggiungiElemento(ElementoCatalogo elemento) {
        em.getTransaction().begin();
        em.persist(elemento);
        em.getTransaction().commit();
    }

    public void rimuoviElemento(String codiceISBN) {
        em.getTransaction().begin();
        ElementoCatalogo elemento = em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.codiceISBN = :codice", ElementoCatalogo.class)
                .setParameter("codice", codiceISBN).getSingleResult();
        em.remove(elemento);
        em.getTransaction().commit();
    }

    public ElementoCatalogo ricercaPerISBN(String codiceISBN) {
        return em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.codiceISBN = :codice", ElementoCatalogo.class)
                .setParameter("codice", codiceISBN).getSingleResult();
    }

    public List<ElementoCatalogo> ricercaPerAnno(int anno) {
        return em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :anno", ElementoCatalogo.class)
                .setParameter("anno", anno).getResultList();
    }

    public List<Libro> ricercaPerAutore(String autore) {
        return em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class)
                .setParameter("autore", autore).getResultList();
    }

    public List<ElementoCatalogo> ricercaPerTitolo(String titolo) {
        return em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.titolo LIKE :titolo", ElementoCatalogo.class)
                .setParameter("titolo", "%" + titolo + "%").getResultList();
    }

    public List<Prestito> ricercaPrestitiInCorso(String numeroTessera) {
        return em.createQuery("SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :tessera AND p.dataRestituzioneEffettiva IS NULL", Prestito.class)
                .setParameter("tessera", numeroTessera).getResultList();
    }

    public List<Prestito> ricercaPrestitiScaduti() {
        return em.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzioneEffettiva IS NULL AND p.dataRestituzionePrevista < CURRENT_DATE", Prestito.class)
                .getResultList();
    }
}
