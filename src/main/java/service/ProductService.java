package service;

import model.Product;
import repository.ProductRepository;

import java.util.List;

public class ProductService {
    private ProductRepository productRepository;

    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    public void deleteProduct(int id) {
        productRepository.delete(id);
    }
}
