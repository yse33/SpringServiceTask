package com.example.exercise.service;

import com.example.exercise.model.ProductElastic;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ElasticService {
    ProductElastic getById(String id);
    List<ProductElastic> getAll();
    ProductElastic save(ProductElastic product);
    ProductElastic update(ProductElastic product, String id);
    void delete(String id);

    List<ProductElastic> getByName(String name);
    List<ProductElastic> getByPriceRange(BigDecimal min, BigDecimal max);
    List<ProductElastic> getByLaunchDateRange(LocalDate start, LocalDate end);
    List<ProductElastic> getByRadius(double lat, double lon, double radius);
    List<ProductElastic> getByGeneralSearch(String query);

    void generateRandomProducts(int count);
}
