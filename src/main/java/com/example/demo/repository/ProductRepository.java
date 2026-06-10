package com.example.demo.repository;

import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
 * Repositorio de Product - acceso a datos con Spring Data JPA
 * @author Valenciano
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Buscar por categoría (ignora mayúsculas)
    List<Product> findByCategoryIgnoreCase(String category);

    // Buscar por nombre (texto parcial)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Filtrar por precio entre min y max
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // Filtrar por stock disponible (stock > 0)
    List<Product> findByStockGreaterThan(int stock);

    // Filtro combinado con JPQL
    @Query("SELECT p FROM Product p WHERE " +
    	       "(:category IS NULL OR p.category = :category) AND " +
    	       "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
    	       "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
    	       "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
    	       "(:onlyInStock = false OR p.stock > 0)")
    List<Product> filterProducts(
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("search") String search,
            @Param("onlyInStock") boolean onlyInStock
    );
}