package com.example.exercise.service.impl;

import com.example.exercise.model.ProductElastic;
import com.example.exercise.repository.ElasticRepository;
import com.example.exercise.service.ElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ElasticServiceImpl implements ElasticService {
    private final ElasticRepository elasticRepository;

    private final Random random = new Random();

    @Override
    public ProductElastic getById(String id) {
        return elasticRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductElastic> getAll() {
        List<ProductElastic> products = new ArrayList<>();
        elasticRepository.findAll().forEach(products::add);
        return products;
    }

    @Override
    public ProductElastic save(ProductElastic product) {
        return elasticRepository.save(product);
    }

    @Override
    public ProductElastic update(ProductElastic product, String id) {
        ProductElastic existingProduct = elasticRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found")
        );

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setLaunchDate(product.getLaunchDate());
        existingProduct.setCoordinatesOfOrigin(product.getCoordinatesOfOrigin());

        return elasticRepository.save(existingProduct);
    }

    @Override
    public void delete(String id) {
        elasticRepository.deleteById(id);
    }

    @Override
    public List<ProductElastic> getByName(String name) {
        return elasticRepository.findByName(name);
    }

    @Override
    public List<ProductElastic> getByPriceRange(BigDecimal min, BigDecimal max) {
        return elasticRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<ProductElastic> getByLaunchDateRange(LocalDate start, LocalDate end) {
        return elasticRepository.findByLaunchDateBetween(start, end);
    }

    @Override
    public List<ProductElastic> getByRadius(double lat, double lon, double radius) {
        if (radius < 0) throw new IllegalArgumentException("Radius must be non-negative");
        if (lat < -90 || lat > 90) throw new IllegalArgumentException("Latitude must be in range [-90, 90]");
        if (lon < -180 || lon > 180) throw new IllegalArgumentException("Longitude must be in range [-180, 180]");

        if (radius == 0) return elasticRepository.findByCoordinatesOfOrigin(new GeoPoint(lat, lon));

        return elasticRepository.findByCoordinatesOfOriginInRadius(lat, lon, radius);
    }

    @Override
    public List<ProductElastic> getByGeneralSearch(String query) {
        return elasticRepository.findByGeneralSearch(query);
    }

    @Override
    public void generateRandomProducts(int count) {
        for (int i = 0; i < count; i++) {
            double lat = BigDecimal.valueOf(-90 + random.nextDouble(180))
                    .setScale(15, RoundingMode.UP).doubleValue();
            double lon = BigDecimal.valueOf(-180 + random.nextDouble(360))
                    .setScale(15, RoundingMode.UP).doubleValue();

            ProductElastic product = ProductElastic.builder()
            .name("Product " + (i + 1))
            .price(BigDecimal.valueOf(random.nextDouble(1000) + 1).setScale(2, RoundingMode.UP))
            .launchDate(LocalDate.ofEpochDay(random.nextInt(365 * 50) + 1))
            .coordinatesOfOrigin(new GeoPoint(lat, lon))
            .build();

            elasticRepository.save(product);
        }
    }
}
