package com.example.exercise.service.impl;

import com.example.exercise.model.Product;
import com.example.exercise.repository.ProductRepository;
import com.example.exercise.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final Random random = new Random();

    @Override
    public Product getById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product, String id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found")
        );

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setLaunchDate(product.getLaunchDate());
        existingProduct.setCoordinatesOfOrigin(product.getCoordinatesOfOrigin());

        return productRepository.save(existingProduct);
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getByPriceRange(BigDecimal min, BigDecimal max) {
        return productRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Product> getByLaunchDateRange(LocalDate start, LocalDate end) {
        return productRepository.findByLaunchDateBetween(start, end);
    }

    @Override
    public void generateRandomProducts(int count) {
        for (int i = 0; i < count; i++) {
            double lat = BigDecimal.valueOf(-90 + random.nextDouble(180))
                    .setScale(15, RoundingMode.UP).doubleValue();
            double lon = BigDecimal.valueOf(-180 + random.nextDouble(360))
                    .setScale(15, RoundingMode.UP).doubleValue();

            Product product = Product.builder()
            .name("Product " + i + 1)
            .price(BigDecimal.valueOf(random.nextDouble(1000) + 1).setScale(2, RoundingMode.UP))
            .launchDate(LocalDate.ofEpochDay(random.nextInt(365 * 50) + 1))
            .coordinatesOfOrigin(new Point(lat, lon))
            .build();

            productRepository.save(product);
        }
    }
}
