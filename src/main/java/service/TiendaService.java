package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Tienda;
import repository.TiendaRepository;

import java.util.List;

public class TiendaService {
    private final TiendaRepository tiendaRepository ;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaUnit");
    private final EntityManager em = emf.createEntityManager();

    public TiendaService() {
        this.tiendaRepository = new TiendaRepository();
    }

    public void save(Tienda tienda) {
        em.getTransaction().begin();
        em.persist(tienda);
        em.getTransaction().commit();
        em.close();
    }

    public void update(Tienda tienda) {
        em.getTransaction().begin();
        em.merge(tienda);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Tienda tienda) {
        em.getTransaction().begin();
        Tienda t = em.find(Tienda.class, tienda.getId());
        if (t != null) {
            em.remove(t);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Tienda getTiendaById(int id) {
        return tiendaRepository.findById(id, em);
    }

    public List<Tienda> getTiendas() {
        return tiendaRepository.findAll(em);
    }

}