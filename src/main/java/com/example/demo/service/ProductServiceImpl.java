package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/*
 * Implementación del servicio de productos
 * Contiene la lógica de negocio para gestionar productos
 * @author Valenciano
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(long id, Product updatedProduct) {
        Product existing = getProduct(id);
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStock(updatedProduct.getStock());
        existing.setCategory(updatedProduct.getCategory());
        return productRepository.save(existing);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> filterProducts(String category, Double minPrice, Double maxPrice,
                                         String search, boolean onlyInStock,
                                         String sortBy, String sortDir) {

        // Obtener productos filtrados desde el repositorio
        List<Product> products = productRepository.filterProducts(
                category, minPrice, maxPrice, search, onlyInStock
        );

        // Ordenación en memoria según el campo solicitado
        Comparator<Product> comparator = switch (sortBy != null ? sortBy : "") {
            case "price" -> Comparator.comparing(Product::getPrice);
            case "stock" -> Comparator.comparing(Product::getStock);
            default      -> Comparator.comparing(Product::getName);
        };

        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }

        products.sort(comparator);
        return products;
    }
}