package com.example.demo.service;

import com.example.demo.domain.Product;
import java.util.List;

/*
 * Interfaz del servicio de productos
 * @author Valenciano
 */
public interface ProductService {

    List<Product> getAllProducts();

    Product getProduct(long id);

    Product saveProduct(Product product);

    Product updateProduct(long id, Product product);

    void deleteProduct(long id);

    List<Product> filterProducts(String category, Double minPrice, Double maxPrice,
                                  String search, boolean onlyInStock, String sortBy, String sortDir);
}