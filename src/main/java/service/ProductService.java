package service;

import model.Product;
import model.Store;
import repository.ProductRepository;
import util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;


    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    public void add(Product product) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (product.getCodigo() != null && em.find(Product.class, product.getCodigo()) != null) {
                em.merge(product);
            } else {
                em.persist(product);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Product> getAllProducts(Store store) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return productRepository.findAll(store, em);
        } finally {
            em.close();
        }
    }

    public Product getProductById(String id, Store store) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return productRepository.findById(id, store, em);
        } finally {
            em.close();
        }
    }

    public List<Product> getProductsByCategory(String category, Store store) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return productRepository.findByCategory(category, store, em);
        } finally {
            em.close();
        }
    }

}
