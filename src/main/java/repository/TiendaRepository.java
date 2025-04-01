package repository;

import model.Tienda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class TiendaRepository {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaUnit");

    public void save(Tienda tienda) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(tienda);
        em.getTransaction().commit();
        em.close();
    }

    public Tienda findById(int id) {
        EntityManager em = emf.createEntityManager();
        Tienda tienda = em.find(Tienda.class, id);
        em.close();
        return tienda;
    }

    public List<Tienda> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Tienda> tiendas = em.createQuery("Select t FROM Tienda t", Tienda.class).getResultList();
        em.close();
        return tiendas;
    }

    public void update(Tienda tienda) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(tienda);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Tienda tienda) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Tienda t = em.find(Tienda.class, tienda.getId());
        if (t != null) {
            em.remove(t);
        }
        em.getTransaction().commit();
        em.close();
    }
}