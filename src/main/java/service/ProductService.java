package service;

import jakarta.persistence.EntityManager;
import model.Product;
import repository.ProductRepository;
import util.JpaUtil;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;


    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    public void update(Product product) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(product);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(String codigo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Product product = em.find(Product.class, codigo);
            if (product != null) {
                em.remove(product);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }


    public List<Product> getAllProducts() {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return productRepository.findAll(em);
        }
    }

    public Product getProductById(String id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return productRepository.findById(id, em);
        }
    }

    public List<Product> getProductsByCategory(String category) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return productRepository.findByCategory(category, em);
        }
    }

}
