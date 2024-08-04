package com.example.exercise.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.domain.Sort;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {
    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void initIndexes() {
        mongoTemplate.indexOps("products").ensureIndex(new Index().on("price", Sort.Direction.ASC));
        mongoTemplate.indexOps("products").ensureIndex(new Index().on("launchDate", Sort.Direction.ASC));
    }
}
