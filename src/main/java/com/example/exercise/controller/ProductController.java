package com.example.exercise.controller;

import com.example.exercise.model.Product;
import com.example.exercise.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ResponseEntity<Product> getProductById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('PRODUCT_READ_ALL')")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('PRODUCT_SAVE')")
    public ResponseEntity<Product> saveProduct(
            @RequestBody Product product
    ) {
        return ResponseEntity.ok(productService.save(product));
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @RequestBody Product product
    ) {
        return ResponseEntity.ok(productService.update(product, id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable String id
    ) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name")
    @PreAuthorize("hasAuthority('PRODUCT_BY_NAME')")
    public ResponseEntity<List<Product>> getProductsByPrice(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(productService.getByName(name));
    }

    @GetMapping("/price")
    @PreAuthorize("hasAuthority('PRODUCT_BY_PRICE')")
    public ResponseEntity<List<Product>> getProductsByPrice(
            @RequestParam Double min,
            @RequestParam Double max
    ) {
        return ResponseEntity.ok(productService.getByPriceRange(new BigDecimal(min), new BigDecimal(max)));
    }

    @GetMapping("/launch-date")
    @PreAuthorize("hasAuthority('PRODUCT_BY_LAUNCH_DATE')")
    public ResponseEntity<List<Product>> getProductsByLaunchDate(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return ResponseEntity.ok(productService.getByLaunchDateRange(LocalDate.parse(start), LocalDate.parse(end)));
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('PRODUCT_GENERATE')")
    public ResponseEntity<Void> generateRandomProducts(
            @RequestParam int count
    ) {
        productService.generateRandomProducts(count);
        return ResponseEntity.ok().build();
    }
}
