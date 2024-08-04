package com.example.exercise.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(indexName = "products")
@TypeAlias("product")
@Data
public class ProductElastic {
    @Id
    private String id;
    private String name;
    private BigDecimal price;
    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    private LocalDate launchDate;
    @GeoPointField
    private GeoPoint coordinatesOfOrigin;
}
