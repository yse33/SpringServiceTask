package com.example.exercise.service;

import com.example.exercise.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ProductService {
    Product getById(String id);
    List<Product> getAll();
    Product save(Product product);
    Product update(Product product, String id);
    void delete(String id);

    List<Product> getByName(String name);
    List<Product> getByPriceRange(BigDecimal min, BigDecimal max);
    List<Product> getByLaunchDateRange(LocalDate start, LocalDate end);

    void generateRandomProducts(int count);
}
