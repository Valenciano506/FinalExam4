package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Implementación del servicio de productos
 * El filtrado se realiza en memoria sobre la lista completa de productos
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
        existing.setYear(updatedProduct.getYear());
        existing.setHorsepower(updatedProduct.getHorsepower());
        existing.setTransmission(updatedProduct.getTransmission());
        existing.setColor(updatedProduct.getColor());
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

        // Traemos todos los productos y filtramos en Java con streams
        List<Product> products = productRepository.findAll();

        products = products.stream()
            .filter(p -> category == null || category.isBlank() ||
                    p.getCategory().equalsIgnoreCase(category))
            .filter(p -> minPrice == null ||
                    p.getPrice() >= minPrice)
            .filter(p -> maxPrice == null ||
                    p.getPrice() <= maxPrice)
            .filter(p -> search == null || search.isBlank() ||
                    p.getName().toLowerCase().contains(search.toLowerCase()))
            .filter(p -> !onlyInStock || p.getStock() > 0)
            .collect(Collectors.toList());

        // Ordenación
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