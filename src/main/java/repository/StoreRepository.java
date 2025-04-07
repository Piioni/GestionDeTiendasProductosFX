package repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Store;


import java.util.List;
import java.util.Optional;

public class StoreRepository {

    public Optional<Store> findById(int id, EntityManager em) {
        try {
            TypedQuery<Store> query = em.createQuery(
                    "SELECT t FROM Store t WHERE t.id = :id",
                    Store.class
            ).setParameter("id", id);

            // Manejo seguro de resultados
            List<Store> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Store> findAll(EntityManager em) {
        try {
            TypedQuery<Store> query = em.createQuery("SELECT t FROM Store t", Store.class);
            List<Store> results = query.getResultList();
            return results.isEmpty() ? List.of() : results;

        } catch (Exception e) {
            return List.of();
        }
    }
}