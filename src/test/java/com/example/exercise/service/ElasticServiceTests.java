package com.example.exercise.service;

import com.example.exercise.model.ProductElastic;
import com.example.exercise.repository.ElasticRepository;
import com.example.exercise.service.impl.ElasticServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ElasticServiceTests {
    @Mock
    private ElasticRepository elasticRepository;

    @InjectMocks
    private ElasticServiceImpl elasticService;

    private ProductElastic product;
    private ProductElastic secondProduct;

    @BeforeEach
    public void setUp() {
        product = ProductElastic.builder()
                .name("product")
                .price(BigDecimal.valueOf(100))
                .launchDate(LocalDate.parse("2015-01-02"))
                .coordinatesOfOrigin(new GeoPoint(1, 1))
                .build();

        secondProduct = ProductElastic.builder()
                .name("secondProduct")
                .price(BigDecimal.valueOf(200))
                .launchDate(LocalDate.parse("2021-01-02"))
                .coordinatesOfOrigin(new GeoPoint(2, 2))
                .build();
    }

    @Test
    public void ElasticService_GetById_ReturnsProduct() {
        when(elasticRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(product));

        ProductElastic result = elasticService.getById("id");

        assertThat(result).isEqualTo(product);
    }

    @Test
    public void ElasticService_GetById_ReturnsNull() {
        when(elasticRepository.findById(any(String.class))).thenReturn(java.util.Optional.empty());

        ProductElastic result = elasticService.getById("id");

        assertThat(result).isNull();
    }

    @Test
    public void ElasticService_GetAll_ReturnsListOfProducts() {
        when(elasticRepository.findAll()).thenReturn(List.of(product, secondProduct));

        List<ProductElastic> result = elasticService.getAll();

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyInAnyOrder(product, secondProduct);
    }

    @Test
    public void ElasticService_Save_ReturnsProduct() {
        when(elasticRepository.save(any(ProductElastic.class))).thenReturn(product);

        ProductElastic result = elasticService.save(product);

        assertThat(result).isEqualTo(product);
    }

    @Test
    public void ElasticService_Update_ReturnsProduct() {
        when(elasticRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(product));
        when(elasticRepository.save(any(ProductElastic.class))).thenReturn(product);

        ProductElastic result = elasticService.update(product, "id");

        assertThat(result).isEqualTo(product);
    }

    @Test
    public void ElasticService_Update_ThrowsRuntimeException() {
        when(elasticRepository.findById(any(String.class))).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> elasticService.update(product, "id"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Product not found");
    }

    @Test
    public void ElasticService_GetByName_ReturnsListOfProducts() {
        when(elasticRepository.findByName(any(String.class))).thenReturn(List.of(product, secondProduct));

        List<ProductElastic> result = elasticService.getByName("name");

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyInAnyOrder(product, secondProduct);
    }

    @Test
    public void ElasticService_GetByPriceRange_ReturnsListOfProducts() {
        when(elasticRepository.findByPriceBetween(any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(List.of(product, secondProduct));

        List<ProductElastic> result = elasticService.getByPriceRange(BigDecimal.valueOf(100), BigDecimal.valueOf(200));

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyInAnyOrder(product, secondProduct);
    }

    @Test
    public void ElasticService_GetByLaunchDateRange_ReturnsListOfProducts() {
        when(elasticRepository.findByLaunchDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(product, secondProduct));

        List<ProductElastic> result = elasticService.getByLaunchDateRange(LocalDate.parse("2015-01-02"), LocalDate.parse("2021-01-02"));

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyInAnyOrder(product, secondProduct);
    }

    @Test
    public void ElasticService_GeneralSearch_ReturnsListOfProducts() {
        when(elasticRepository.findByGeneralSearch(any(String.class)))
                .thenReturn(List.of(product, secondProduct));

        List<ProductElastic> result = elasticService.getByGeneralSearch("query");

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyInAnyOrder(product, secondProduct);
    }

    @Test
    public void ElasticService_GetByRadius_ReturnsListOfProducts() {
        when(elasticRepository.findByCoordinatesOfOriginInRadius(any(Double.class), any(Double.class), any(Double.class))
        ).thenReturn(List.of(product, secondProduct));

        List<ProductElastic> result = elasticService.getByRadius(1, 1, 1);

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyInAnyOrder(product, secondProduct);
    }

    @Test
    public void ElasticSearch_GetByRadius_ThrowsIllegalArgumentException_LatitudeOutOfRange_Over() {
        assertThatThrownBy(() -> elasticService.getByRadius(91, 1, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Latitude must be in range [-90, 90]");
    }

    @Test
    public void ElasticSearch_GetByRadius_ThrowsIllegalArgumentException_LatitudeOutOfRange_Under() {
        assertThatThrownBy(() -> elasticService.getByRadius(-91, 1, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Latitude must be in range [-90, 90]");
    }

    @Test
    public void ElasticSearch_GetByRadius_ThrowsIllegalArgumentException_LongitudeOutOfRange_Over() {
        assertThatThrownBy(() -> elasticService.getByRadius(1, 181, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Longitude must be in range [-180, 180]");
    }

    @Test
    public void ElasticSearch_GetByRadius_ThrowsIllegalArgumentException_LongitudeOutOfRange_Under() {
        assertThatThrownBy(() -> elasticService.getByRadius(1, -181, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Longitude must be in range [-180, 180]");
    }

    @Test
    public void ElasticSearch_GetByRadius_ThrowsIllegalArgumentException_NegativeRadius() {
        assertThatThrownBy(() -> elasticService.getByRadius(1, 1, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Radius must be non-negative");
    }
}
