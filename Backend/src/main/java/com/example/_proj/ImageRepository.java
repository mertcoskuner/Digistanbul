package com.example._proj;

import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.Optional;

public interface ImageRepository extends MongoRepository<ImageSource, String> {
    Optional<ImageSource> findByName(String fileName);

}
