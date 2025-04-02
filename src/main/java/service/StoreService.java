package service;

import model.Store;
import repository.StoreRepository;
import util.JpaUtil;
import javax.persistence.EntityManager;
import java.util.List;

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


    public List<Store> getTiendas() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return storeRepository.findAll(em);
        } finally {
            em.close();
        }
    }

}