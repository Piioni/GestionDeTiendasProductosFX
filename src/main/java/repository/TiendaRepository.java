package repository;

import model.Tienda;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TiendaRepository {

    public Tienda findById(int id, EntityManager em) {
        Tienda tienda = em.find(Tienda.class, id);
        em.close();
        return tienda;
    }

    public List<Tienda> findAll(EntityManager em) {
        List<Tienda> tiendas = em.createQuery("Select t FROM Tienda t", Tienda.class).getResultList();
        em.close();
        return tiendas;
    }


}