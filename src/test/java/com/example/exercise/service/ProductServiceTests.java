package com.example.exercise.service;

import com.example.exercise.model.Product;
import com.example.exercise.repository.ProductRepository;
import com.example.exercise.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.geo.Point;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private Product secondProduct;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .name("product")
                .price(BigDecimal.valueOf(100))
                .launchDate(LocalDate.parse("2015-01-02"))
                .coordinatesOfOrigin(new Point(1, 1))
                .build();

        secondProduct = Product.builder()
                .name("secondProduct")
                .price(BigDecimal.valueOf(200))
                .launchDate(LocalDate.parse("2021-01-02"))
                .coordinatesOfOrigin(new Point(2, 2))
                .build();
    }

    @Test
    public void ProductService_GetById_ReturnsProduct() {
        when(productRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(product));

        Product result = productService.getById("id");

        assertThat(result).isEqualTo(product);
    }

    @Test
    public void ProductService_GetById_ReturnsNull() {
        when(productRepository.findById(any(String.class))).thenReturn(java.util.Optional.empty());

        Product result = productService.getById("id");

        assertThat(result).isNull();
    }

    @Test
    public void ProductService_GetAll_ReturnsListOfProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product, secondProduct));

        List<Product> result = productService.getAll();

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyInAnyOrder(product, secondProduct);
    }

    @Test
    public void ProductService_Save_ReturnsProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.save(product);

        assertThat(result).isEqualTo(product);
    }

    @Test
    public void ProductService_Update_ReturnsProduct() {
        when(productRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.update(secondProduct, "id");

        assertThat(result).isEqualTo(secondProduct);
    }

    @Test
    public void ProductService_Update_ThrowsRuntimeException() {
        when(productRepository.findById(any(String.class))).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> productService.update(secondProduct, "id"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Product not found");
    }

    @Test
    public void ProductService_GetByName_ReturnsListOfProducts() {
        when(productRepository.findByName(any(String.class))).thenReturn(List.of(product, secondProduct));

        List<Product> result = productService.getByName("product");

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyInAnyOrder(product, secondProduct);
    }

    @Test
    public void ProductService_GetByPriceRange_ReturnsListOfProducts() {
        when(productRepository.findByPriceBetween(any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(List.of(product, secondProduct));

        List<Product> result = productService.getByPriceRange(BigDecimal.valueOf(100), BigDecimal.valueOf(200));

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyInAnyOrder(product, secondProduct);
    }

    @Test
    public void ProductService_GetByLaunchDateRange_ReturnsListOfProducts() {
        when(productRepository.findByLaunchDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(product, secondProduct));

        List<Product> result = productService.getByLaunchDateRange(LocalDate.parse("2015-01-02"), LocalDate.parse("2021-01-02"));

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyInAnyOrder(product, secondProduct);
    }
}
