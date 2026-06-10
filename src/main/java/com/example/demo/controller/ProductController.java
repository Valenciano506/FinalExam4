package com.example.demo.controller;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Controlador REST para productos
 * Expone los endpoints de la API para gestión de productos
 * @author Valenciano
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET /api/products → todos los productos, con filtros opcionales
    // Ejemplo: /api/products?category=gaming&minPrice=100&sortBy=price&sortDir=asc
    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "false") boolean onlyInStock,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return productService.filterProducts(category, minPrice, maxPrice, search, onlyInStock, sortBy, sortDir);
    }

    // GET /api/products/{id} → un producto por id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        try {
            return ResponseEntity.ok(productService.getProduct(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/products → crear producto (solo ADMIN)
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    // PUT /api/products/{id} → actualizar producto (solo ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/products/{id} → eliminar producto (solo ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}