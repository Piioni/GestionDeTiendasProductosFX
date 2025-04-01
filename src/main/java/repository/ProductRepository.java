package repository;

import jakarta.persistence.EntityManager;
import model.Product;

import java.util.List;

public class ProductRepository {

    public List<Product> findAll(EntityManager em) {
        List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        em.close();
        return products;
    }

    public Product findById(String id, EntityManager em) {
        Product product = em.find(Product.class, id);
        em.close();
        return product;
    }

    public List<Product> findByCategory(String category, EntityManager em) {
        List<Product> products = em.createQuery("SELECT p FROM Product p WHERE p.categoria = :category", Product.class)
                .setParameter("category", category)
                .getResultList();
        em.close();
        return products;
    }


}
