package service;

import jakarta.persistence.EntityManager;
import model.Store;
import repository.StoreRepository;
import util.JpaUtil;

import java.util.List;
import java.util.Optional;

public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService() {
        this.storeRepository = new StoreRepository();
    }

    public void save(Store tienda) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(tienda);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void update(Store tienda) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(tienda);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Store tienda) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Store tiendaToDelete = em.find(Store.class, tienda.getId());
            if (tiendaToDelete != null) {
                em.remove(tiendaToDelete);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Optional<Store> getTiendaById(int id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return storeRepository.findById(id, em);
        }
    }

    public List<Store> getTiendas() {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return storeRepository.findAll(em);
        }
    }

}