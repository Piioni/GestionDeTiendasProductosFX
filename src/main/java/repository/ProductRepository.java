package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Product;

import java.util.List;

public class ProductRepository {

    public List<Product> findAll(EntityManager em) {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
        List<Product> products = query.getResultList();
        em.close();
        return products;
    }

    public Product findById(String id, EntityManager em) {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.codigo = :id", Product.class).
                setParameter("id", id);
        Product product = query.getSingleResult();
        em.close();
        return product;
    }

    public List<Product> findByCategory(String category, EntityManager em) {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.categoria = :category", Product.class)
                .setParameter("category", category);
        List<Product> products = query.getResultList();
        em.close();
        return products;
    }


}
