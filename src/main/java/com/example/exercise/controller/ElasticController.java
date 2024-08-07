package com.example.exercise.controller;

import com.example.exercise.model.ProductElastic;
import com.example.exercise.service.ElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('PRODUCT_ELASTIC_READ')")
    public ResponseEntity<ProductElastic> getProductById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(elasticService.getById(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('PRODUCT_ELASTIC_READ_ALL')")
    public ResponseEntity<List<ProductElastic>> getAllProducts() {
        return ResponseEntity.ok(elasticService.getAll());
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('PRODUCT_ELASTIC_SAVE')")
    public ResponseEntity<ProductElastic> saveProduct(
            @RequestBody ProductElastic product
    ) {
        return ResponseEntity.ok(elasticService.save(product));
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_ELASTIC_UPDATE')")
    public ResponseEntity<ProductElastic> updateProduct(
            @PathVariable String id,
            @RequestBody ProductElastic product
    ) {
        return ResponseEntity.ok(elasticService.update(product, id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_ELASTIC_DELETE')")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable String id
    ) {
        elasticService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name")
    @PreAuthorize("hasAuthority('PRODUCT_ELASTIC_BY_NAME')")
    public ResponseEntity<List<ProductElastic>> getProductsByName(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(elasticService.getByName(name));
    }

    @GetMapping("/price")
    @PreAuthorize("hasAuthority('PRODUCT_ELASTIC_BY_PRICE')")
    public ResponseEntity<List<ProductElastic>> getProductsByPrice(
            @RequestParam Double min,
            @RequestParam Double max
    ) {
        return ResponseEntity.ok(elasticService.getByPriceRange(new BigDecimal(min), new BigDecimal(max)));
    }

    @GetMapping("/launch-date")
    @PreAuthorize("hasAuthority('PRODUCT_ELASTIC_BY_LAUNCH_DATE')")
    public ResponseEntity<List<ProductElastic>> getProductsByLaunchDate(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return ResponseEntity.ok(elasticService.getByLaunchDateRange(LocalDate.parse(start), LocalDate.parse(end)));
    }

    @GetMapping("/radius")
    @PreAuthorize("hasAuthority('PRODUCT_ELASTIC_BY_RADIUS')")
    public ResponseEntity<List<ProductElastic>> getProductsByRadius(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam double radius
    ) {
        return ResponseEntity.ok(elasticService.getByRadius(lat, lon, radius));
    }

    @GetMapping("/general-search")
    @PreAuthorize("hasAuthority('PRODUCT_ELASTIC_BY_GENERAL_SEARCH')")
    public ResponseEntity<List<ProductElastic>> getProductsByGeneralSearch(
            @RequestParam String query
    ) {
        return ResponseEntity.ok(elasticService.getByGeneralSearch(query));
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('PRODUCT_ELASTIC_GENERATE')")
    public ResponseEntity<Void> generateRandomProducts(
            @RequestParam Integer count
    ) {
        elasticService.generateRandomProducts(count);
        return ResponseEntity.ok().build();
    }
}
