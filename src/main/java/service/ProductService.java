package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Product;
import repository.ProductRepository;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaUnit");
    private final EntityManager em = emf.createEntityManager();

    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    public void update(Product product) {
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(String codigo) {
        em.getTransaction().begin();
        Product product = em.find(Product.class, codigo);
        if (product != null) {
            em.remove(product);
        }
        em.getTransaction().commit();
        em.close();
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll(em);
    }

    public Product getProductById(String id) {
        return productRepository.findById(id, em);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category, em);
    }

}
