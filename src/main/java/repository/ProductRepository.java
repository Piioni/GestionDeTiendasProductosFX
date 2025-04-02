package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Product;
import model.Store;

import java.util.List;

public class ProductRepository {

    public List<Product> findAll(Store store, EntityManager em) {
        try {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.tienda = :store", Product.class)
                    .setParameter("store", store);
            List<Product> results = query.getResultList();
            return results.isEmpty() ? List.of() : results;
        } catch (Exception e) {
            System.out.println("Error al obtener los productos de la tienda: " + store.getNombre());
            return List.of();
        }
    }

    public Product findById(String id, Store store, EntityManager em) {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.codigo = :id AND p.tienda = :store", Product.class)
                .setParameter("id", id)
                .setParameter("store", store);
        Product product = null;
        try {
            product = query.getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            System.out.println("No se encontró el producto con el ID: " + id + " en la tienda: " + store.getNombre());
        }
        return product;
    }

    public List<Product> findByCategory(String category, Store store, EntityManager em) {
        try {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.categoria = :category AND p.tienda = :store", Product.class)
                    .setParameter("category", category)
                    .setParameter("store", store);
            List<Product> products = query.getResultList();
            em.close();
            return products.isEmpty() ? List.of() : products;
        } catch (Exception e) {
            System.out.println("Error al obtener los productos de la tienda: " + store.getNombre());
        }
        return List.of();
    }


}
