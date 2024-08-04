package com.example.exercise.controller;

import com.example.exercise.model.ProductElastic;
import com.example.exercise.service.ElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/elastic")
public class ElasticController {
    private final ElasticService elasticService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductElastic> getProductById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(elasticService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductElastic>> getAllProducts() {
        return ResponseEntity.ok(elasticService.getAll());
    }

    @PostMapping("/save")
    public ResponseEntity<ProductElastic> saveProduct(
            @RequestBody ProductElastic product
    ) {
        return ResponseEntity.ok(elasticService.save(product));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ProductElastic> updateProduct(
            @PathVariable String id,
            @RequestBody ProductElastic product
    ) {
        return ResponseEntity.ok(elasticService.update(product, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable String id
    ) {
        elasticService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name")
    public ResponseEntity<List<ProductElastic>> getProductsByName(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(elasticService.getByName(name));
    }

    @GetMapping("/price")
    public ResponseEntity<List<ProductElastic>> getProductsByPrice(
            @RequestParam Double min,
            @RequestParam Double max
    ) {
        return ResponseEntity.ok(elasticService.getByPriceRange(new BigDecimal(min), new BigDecimal(max)));
    }

    @GetMapping("/launch-date")
    public ResponseEntity<List<ProductElastic>> getProductsByLaunchDate(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return ResponseEntity.ok(elasticService.getByLaunchDateRange(LocalDate.parse(start), LocalDate.parse(end)));
    }

    @GetMapping("/radius")
    public ResponseEntity<List<ProductElastic>> getProductsByRadius(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam double radius
    ) {
        return ResponseEntity.ok(elasticService.getByRadius(lat, lon, radius));
    }

    @GetMapping("/general-search")
    public ResponseEntity<List<ProductElastic>> getProductsByGeneralSearch(
            @RequestParam String query
    ) {
        return ResponseEntity.ok(elasticService.getByGeneralSearch(query));
    }

    @PostMapping("/generate")
    public ResponseEntity<Void> generateRandomProducts(
            @RequestParam Integer count
    ) {
        elasticService.generateRandomProducts(count);
        return ResponseEntity.ok().build();
    }
}
