package com.example.exercise.repository;

import com.example.exercise.model.ProductElastic;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ElasticRepository extends ElasticsearchRepository<ProductElastic, String> {
    List<ProductElastic> findByName(String name);
    List<ProductElastic> findByPriceBetween(BigDecimal min, BigDecimal max);
    List<ProductElastic> findByLaunchDateBetween(LocalDate start, LocalDate end);
    @Query("{\"bool\": {\"filter\": {\"geo_distance\": {\"distance\": \"?2km\", \"coordinatesOfOrigin\": {\"lat\": ?0, \"lon\": ?1}}}}}")
    List<ProductElastic> findByCoordinatesOfOriginInRadius(double lat, double lon, double radius);
    @Query("{\"query_string\": { \"query\": \"?0\" } }")
    List<ProductElastic> findByGeneralSearch(String query);
}
